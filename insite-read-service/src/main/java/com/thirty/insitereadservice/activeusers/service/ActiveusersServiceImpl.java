package com.thirty.insitereadservice.activeusers.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.activeusers.dto.ActiveTimeDto;
import com.thirty.insitereadservice.activeusers.dto.request.ActiveUsersPerTimeReqDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUsersPerTimeResDto;
import com.thirty.insitereadservice.activeusers.dto.OsActiveUserDto;
import com.thirty.insitereadservice.activeusers.dto.request.ActiveUserReqDto;
import com.thirty.insitereadservice.activeusers.dto.request.AverageActiveTimeReqDto;
import com.thirty.insitereadservice.activeusers.dto.request.OsActiveUserReqDto;
import com.thirty.insitereadservice.activeusers.dto.response.ActiveUserResDto;
import com.thirty.insitereadservice.activeusers.dto.response.AverageActiveTimeResDto;
import com.thirty.insitereadservice.activeusers.dto.response.OsActiveUserResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.TimeException;
import feign.FeignException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActiveusersServiceImpl implements ActiveusersService {

    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ActiveUsersPerTimeResDto getActiveUsersPerTime(ActiveUsersPerTimeReqDto activeUsersPerTimeReqDto, int memberId) {
        String token = activeUsersPerTimeReqDto.getApplicationToken();
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));

        Instant startInstant = activeUsersPerTimeReqDto.getStartDate().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = activeUsersPerTimeReqDto.getEndDate().plusHours(9).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux query = Flux.from("insite")
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("activityId");

        log.info("query = {}" ,query);

        //해당 값 가져와 이름별로 각 숫자 저장
        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, ActiveTimeDto> activeUserWithActiveTimeMap = new HashMap<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            if (!records.isEmpty()) {
                int size = records.size();
                String activityId = records.get(0).getValueByKey("activityId").toString();

                //날짜 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime startTime = LocalDateTime.parse(records.get(0).getValueByKey("_time").toString(), formatter);

                LocalDateTime endTime;

                if(size == 1){
                    endTime = startTime.plusMinutes(30L);
                }else{
                    endTime = LocalDateTime.parse(records.get(size-1).getValueByKey("_time").toString(), formatter).plusMinutes(30L);
                }

                activeUserWithActiveTimeMap.put(activityId, ActiveTimeDto.create(startTime,endTime));
            }
        }

        return ActiveUsersPerTimeResDto.calculate(activeUserWithActiveTimeMap);
    }

    @Override
    public ActiveUserResDto getActiveUserCount(ActiveUserReqDto activeUserReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(activeUserReqDto.getApplicationToken(),memberId));

        //범위 시간 지정
        Instant startInstant = activeUserReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = activeUserReqDto.getEndDateTime().plusHours(9).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant)  || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(activeUserReqDto.getApplicationToken())
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("activityId")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();
        log.info("query ={}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());

        return ActiveUserResDto.create(tables.size());
    }

    @Override
    public AverageActiveTimeResDto getAverageActiveTime(
        AverageActiveTimeReqDto averageActiveTimeReqDto,int memberId) {

//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(averageActiveTimeReqDto.getApplicationToken(),memberId));

        //범위 시간 지정
        Instant startInstant = averageActiveTimeReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = averageActiveTimeReqDto.getEndDateTime().plusHours(9).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(averageActiveTimeReqDto.getApplicationToken())
        );
        Flux query = Flux.from(bucket)
            .range(0L)
            .filter(restrictions)
            .groupBy("activityId");
        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());

        double size = tables.size();
        double time=0;

        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            if(records.size()<=1)
                continue;
            try {
                StringTokenizer st = new StringTokenizer(records.get(0).getValueByKey("_time").toString(),"T");
                String from ="";
                from+=st.nextToken();
                from+=" ";
                from+=st.nextToken();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
                Date fromDate = transFormat.parse(from);
                st = new StringTokenizer(records.get(records.size() - 1).getValueByKey("_time").toString(),"T");
                String to="";
                to+=st.nextToken();
                to+=" ";
                to+=st.nextToken();

                Date toDate = transFormat.parse(to);
                long sec =(toDate.getTime()-fromDate.getTime())/1000;
                time+=sec;
            }
            catch(Exception e){
                log.error(e.getMessage());
            }
        }
        return AverageActiveTimeResDto.create(time/size);
    }


    @Override
    public OsActiveUserResDto getOsActiveUserCounts(OsActiveUserReqDto osActiveUserReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(osActiveUserReqDto.getApplicationToken(),memberId));

        //범위 시간 설정
        Instant startInstant = osActiveUserReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = osActiveUserReqDto.getEndDateTime().plusHours(9).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(osActiveUserReqDto.getApplicationToken())
        );

        Flux query = Flux.from(bucket)
            .range(startInstant,endInstant)
            .filter(restrictions)
            .groupBy("osId")
            .count();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());

        List<OsActiveUserDto> osActiveUserDtoList = new ArrayList<>();
        PriorityQueue<OsActiveUserDto> osActiveUserDtoPriorityQueue = new PriorityQueue<>();

        for(FluxTable fluxTable :tables){
            List<FluxRecord> records = fluxTable.getRecords();
            for(FluxRecord record:records){

                String os = record.getValueByKey("osId").toString();
                String stringValueOfCount = record.getValueByKey("_value").toString();
                int count = Integer.valueOf(stringValueOfCount);

                osActiveUserDtoPriorityQueue.offer(OsActiveUserDto.create(os,count));
            }
        }
        int id = 0;
        while(!osActiveUserDtoPriorityQueue.isEmpty()){
            osActiveUserDtoList.add(osActiveUserDtoPriorityQueue.poll().addId(id++));
        }

        return OsActiveUserResDto.from(osActiveUserDtoList);
    }
}
