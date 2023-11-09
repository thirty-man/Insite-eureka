package com.thirty.insitereadservice.activeusers.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.activeusers.dto.*;
import com.thirty.insitereadservice.activeusers.dto.request.*;
import com.thirty.insitereadservice.activeusers.dto.response.*;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.TimeException;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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

        Instant startInstant = activeUsersPerTimeReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = activeUsersPerTimeReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

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
    public ActiveUserResDto getActiveUser(ActiveUserReqDto activeUserReqDto, int memberId) {
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
            .groupBy("currentUrl")
//            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
                .distinct("activityId")
                .sort(new String[] {"_time"},true)
            .yield();

        log.info("query ={}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        PriorityQueue<ActiveUserDto> activeUserDtoPriorityQueue = new PriorityQueue<>();
        List<ActiveUserDto> activeUserDtoList = new ArrayList<>();
        HashMap<String,ActiveUserDto> map = new HashMap<>();
        int id=0;
        double size=0;

        for(FluxTable table:tables){
            List<FluxRecord> records=table.getRecords();
            size+=records.size();
            String currentUrl= records.get(0).getValueByKey("currentUrl").toString();
            activeUserDtoPriorityQueue.add(ActiveUserDto.create(currentUrl,records.size()));
        }
        while(!activeUserDtoPriorityQueue.isEmpty()){
            activeUserDtoList.add(activeUserDtoPriorityQueue.poll().add(id++,size));
        }
        return ActiveUserResDto.create(activeUserDtoList);
    }

    @Override
    public AverageActiveTimeResDto getAverageActiveTime(
        AverageActiveTimeReqDto averageActiveTimeReqDto,int memberId) {

//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(averageActiveTimeReqDto.getApplicationToken(),memberId));

        //범위 시간 지정
        Instant startInstant = averageActiveTimeReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = averageActiveTimeReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

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
            .groupBy(new String [] {"currentUrl","activityId"})
                .sort(new String[]{"_time"},true);
        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        HashMap<String,Double> map = new HashMap<>();
        HashMap<String,Integer> size = new HashMap<>();
        PriorityQueue<AverageActiveTimeDto> averageActiveTimeDtoPriorityQueue= new PriorityQueue<>();
        List<AverageActiveTimeDto> averageActiveTimeDtoList= new ArrayList<>();
        int id=0;
        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            if(records.size()<=1)
                continue;
            String currentUrl = records.get(0).getValueByKey("currentUrl").toString();
            if(map.containsKey(currentUrl)) {
                size.replace(currentUrl,size.get(currentUrl)+1);
            }
            else{
                size.put(currentUrl,1);
            }
            try {
                StringTokenizer st = new StringTokenizer(records.get(records.size() - 1).getValueByKey("_time").toString(),"T");
                String from ="";
                from+=st.nextToken();
                from+=" ";
                from+=st.nextToken();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
                Date fromDate = transFormat.parse(from);
                st = new StringTokenizer(records.get(0).getValueByKey("_time").toString(),"T");
                String to="";
                to+=st.nextToken();
                to+=" ";
                to+=st.nextToken();


                Date toDate = transFormat.parse(to);
                double sec =(toDate.getTime()-fromDate.getTime())/1000;
                log.info("sec={}",sec);
                if(map.containsKey(currentUrl)){
                    map.replace(currentUrl,map.get(currentUrl)+sec);
                }
                else{
                    map.put(currentUrl,sec);
                }
            }
            catch(Exception e){
                log.error(e.getMessage());
            }


        }
        map.forEach((key,value)->{
            String current = key;
            double average = value/size.get(current);
            log.info("average={}",average);
            averageActiveTimeDtoPriorityQueue.add(AverageActiveTimeDto.create(current,average));

        });
        while(!averageActiveTimeDtoPriorityQueue.isEmpty()){
            averageActiveTimeDtoList.add(averageActiveTimeDtoPriorityQueue.poll().addId(id++));
        }
        return AverageActiveTimeResDto.create(averageActiveTimeDtoList);
    }


    @Override
    public OsActiveUserResDto getOsActiveUserCounts(OsActiveUserReqDto osActiveUserReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(osActiveUserReqDto.getApplicationToken(),memberId));

        //범위 시간 설정
        Instant startInstant = osActiveUserReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = osActiveUserReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

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

    @Override
    public ActiveUserCountResDto getActiveUserCount(ActiveUserCountReqDto activeUserCountReqDto, int memberId) {
        //        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(osActiveUserReqDto.getApplicationToken(),memberId));

        //범위 시간 설정
        Instant startInstant = activeUserCountReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = activeUserCountReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(activeUserCountReqDto.getApplicationToken())
        );

        Flux query = Flux.from(bucket)
                .range(startInstant,endInstant)
                .filter(restrictions)
                .groupBy("activityId")
                .count();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        int size = tables.size();

        return ActiveUserCountResDto.create(size);
    }

    @Override
    public ViewCountsPerActiveUserResDto getViewCounts(ViewCountsPerActiveUserReqDto viewCountsPerActiveUserReqDto, int memberId) {
        //        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(osActiveUserReqDto.getApplicationToken(),memberId));

        //범위 시간 설정
        Instant startInstant = viewCountsPerActiveUserReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = viewCountsPerActiveUserReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }
        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(viewCountsPerActiveUserReqDto.getApplicationToken())
        );

        Flux act_query = Flux.from(bucket)
                .range(startInstant,endInstant)
                .filter(restrictions)
                .groupBy("currentUrl")
                .distinct("activityId")
                .count();

        log.info("query= {}", act_query);

        QueryApi act_queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = act_queryApi.query(act_query.toString());
        HashMap<String,Integer> map=new HashMap<>();
        for(FluxTable table:tables){
            List<FluxRecord> records= table.getRecords();
            String currentUrl= records.get(0).getValueByKey("currentUrl").toString();
            int count = Integer.parseInt(records.get(0).getValueByKey("_value").toString());
            map.put(currentUrl,count);
        }


//      위는 currentUrl에 해당하는 activityId 갯수
//      아래는 currentUrl에 해당하는 총 조회수 
        Restrictions act_restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(viewCountsPerActiveUserReqDto.getApplicationToken())
        );

        Flux query = Flux.from(bucket)
                .range(startInstant,endInstant)
                .filter(restrictions)
                .groupBy("currentUrl")
                .count();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> Fluxtables = queryApi.query(query.toString());
        PriorityQueue<ViewCountsPerActiveUserDto> priorityQueue = new PriorityQueue<>();
        List<ViewCountsPerActiveUserDto> viewCountsPerActiveUserDtoList= new ArrayList<>();
        int id=0;
        for(FluxTable table:Fluxtables){
            List<FluxRecord> records= table.getRecords();
            String currentUrl= records.get(0).getValueByKey("currentUrl").toString();
            int count = Integer.parseInt(records.get(0).getValueByKey("_value").toString());
            int act=map.get(currentUrl);
            priorityQueue.add(ViewCountsPerActiveUserDto.create(currentUrl,count,act));

        }
        while(!priorityQueue.isEmpty()){
            viewCountsPerActiveUserDtoList.add(priorityQueue.poll().add(id++));
        }

        return ViewCountsPerActiveUserResDto.create(viewCountsPerActiveUserDtoList);
    }

    @Override
    public ActiveUserPerUserResDto getActiveUserPerUser(ActiveUserPerUserReqDto activeUserPerUserReqDto, int memberId) {
        //        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(osActiveUserReqDto.getApplicationToken(),memberId));

        //범위 시간 설정
        Instant startInstant = activeUserPerUserReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = activeUserPerUserReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }
        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(activeUserPerUserReqDto.getApplicationToken())
        );

        Flux act_query = Flux.from(bucket)
                .range(startInstant,endInstant)
                .filter(restrictions)
                .groupBy("currentUrl")
                .distinct("activityId")
                .count();

        log.info("query= {}", act_query);

        QueryApi act_queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = act_queryApi.query(act_query.toString());
        HashMap<String, Integer> map = new HashMap<>();
        for(FluxTable table:tables){
            List<FluxRecord> records = table.getRecords();
            String currentUrl=records.get(0).getValueByKey("currentUrl").toString();
            int count = Integer.parseInt(records.get(0).getValueByKey("_value").toString());
            map.put(currentUrl,count);
        }
        //위는 currentUrl에 따른 활동 사용자수 map에 저장

        // 아래는 currentUrl에 따른 사용자 수 map에 저장
        restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(activeUserPerUserReqDto.getApplicationToken())
        );

        Flux query = Flux.from(bucket)
                .range(startInstant, endInstant)
                .filter(restrictions)
                .groupBy("currentUrl")
                .distinct("cookieId")
                .sort(new String[]{"_time"},true)
                .count();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> fluxTables = queryApi.query(query.toString());
        HashMap<String,Integer> cookie = new HashMap<>();
        for(FluxTable table:fluxTables){
            List<FluxRecord> records = table.getRecords();
            String currentUrl=records.get(0).getValueByKey("currentUrl").toString();
            int count = Integer.parseInt(records.get(0).getValueByKey("_value").toString());
            cookie.put(currentUrl,count);
        }
        PriorityQueue<ActiveUserPerUserDto> activeUserPerUserDtoPriorityQueue = new PriorityQueue<>();
        List<ActiveUserPerUserDto> activeUserPerUserDtoList=new ArrayList<>();
        int id=0;
        map.forEach((key,value)->{
            String currentUrl = key;
            int size= cookie.get(key);
            activeUserPerUserDtoPriorityQueue.add(ActiveUserPerUserDto.create(currentUrl,(double)value/(double) size));
        });
        while(!activeUserPerUserDtoPriorityQueue.isEmpty()){
            activeUserPerUserDtoList.add(activeUserPerUserDtoPriorityQueue.poll().addId(id++));
        }
        return ActiveUserPerUserResDto.create(activeUserPerUserDtoList);
    }
}
