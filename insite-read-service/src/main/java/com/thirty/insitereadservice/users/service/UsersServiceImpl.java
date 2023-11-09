package com.thirty.insitereadservice.users.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.activeusers.dto.request.ViewCountsPerActiveUserReqDto;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.TimeException;
import com.thirty.insitereadservice.users.dto.*;
import com.thirty.insitereadservice.users.dto.request.*;
import com.thirty.insitereadservice.users.dto.response.*;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import javax.annotation.Resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public AbnormalHistoryResDto getAbnormalHistory(AbnormalHistoryReqDto abnormalHistoryReqDto, int memberId) {
        String applicationToken = abnormalHistoryReqDto.getApplicationToken();
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken,memberId));

        //통계 시간 설정
        Instant startInstant = abnormalHistoryReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = abnormalHistoryReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from(bucket: \"").append(bucket).append("\")\n");
        queryBuilder.append("  |> range(start: ").append(startInstant).append(", stop:").append(endInstant).append(")\n");
        queryBuilder.append("  |> filter(fn: (r) => r._measurement == \"data\" and r.applicationToken == \"")
            .append(applicationToken).append("\" and float(v: r.requestCnt) >= 10)\n");
        queryBuilder.append("  |> group(columns:[\"\"])\n");
        queryBuilder.append("  |> sort(columns: [\"_time\"])");

        log.info("query = {}" ,queryBuilder);

        //해당 값 가져와 이름별로 각 숫자 저장
        List<FluxTable> tables = queryApi.query(queryBuilder.toString());
        List<AbnormalDto> abnormalDtoList = new ArrayList<>();
        int id = 0 ;

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record: records) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime date = LocalDateTime.parse(record.getValueByKey("_time").toString(), formatter);

                String cookieId = record.getValueByKey("cookieId").toString();
                String currentUrl = record.getValueByKey("currentUrl").toString();
                String language = record.getValueByKey("language").toString();
                String stringValueOfRequestCnt = record.getValueByKey("requestCnt").toString();
                String osId = record.getValueByKey("osId").toString();

                int requestCnt = Integer.valueOf(stringValueOfRequestCnt);
                abnormalDtoList.add(AbnormalDto.create(cookieId,date,currentUrl,language,requestCnt,osId).addId(id++));
            }
        }

        return AbnormalHistoryResDto.create(abnormalDtoList);
    }

    //
    @Override
    public PageViewResDto getPageView(PageViewReqDto pageViewReqDto, int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(pageViewReqDto.getApplicationToken(), memberId));

        //통계 시간 설정
        Instant startInstant = pageViewReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = pageViewReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(pageViewReqDto.getApplicationToken())
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("currentUrl")
                .sort(new String[]{"_time"},true)
                .count()
            .yield();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        List<PageViewDto>pageViewDtoList = new ArrayList<>();
        PriorityQueue<PageViewDto> pageViewDtoPriorityQueue = new PriorityQueue<>();
        int id=0;

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            String currentUrl = records.get(0).getValueByKey("currentUrl").toString();
            int count =Integer.parseInt(records.get(0).getValueByKey("_value").toString());
            pageViewDtoPriorityQueue.add(PageViewDto.create(currentUrl,count));
        }
        while(!pageViewDtoPriorityQueue.isEmpty()){
            pageViewDtoList.add(pageViewDtoPriorityQueue.poll().addId(id++));
        }

        return PageViewResDto.create(pageViewDtoList);
    }

    @Override
    public UserCountResDto getUserCount(UserCountReqDto userCountReqDto, int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(userCountReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = userCountReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = userCountReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(userCountReqDto.getApplicationToken())
        );

        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("currentUrl")
                .distinct("cookieId")
                .sort(new String[]{"_time"},true)
            .yield();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        List<UserCountDto>userCountDtoList =new ArrayList<>();
        PriorityQueue<UserCountDto> userCountDtoPriorityQueue = new PriorityQueue<>();
        int id=0;
        for(FluxTable table:tables){
            List<FluxRecord>records = table.getRecords();
            String currentUrl=records.get(0).getValueByKey("currentUrl").toString();
            int count = records.size();
            userCountDtoPriorityQueue.add(UserCountDto.create(currentUrl,count));

        }
        while(!userCountDtoPriorityQueue.isEmpty()){
            userCountDtoList.add(userCountDtoPriorityQueue.poll().addId(id++));
        }


        return UserCountResDto.create(userCountDtoList);
    }

    @Override
    public TotalUserCountResDto getTotalUserCount(TotalUserCountReqDto totalUserCountReqDto, int memberId) {
        //        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(userCountReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = totalUserCountReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = totalUserCountReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(totalUserCountReqDto.getApplicationToken())
        );

        Flux query = Flux.from(bucket)
                .range(startInstant, endInstant)
                .filter(restrictions)
                .groupBy("cookieId")
                .count()
                .yield();
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        int size=tables.size();

        return TotalUserCountResDto.create(size);
    }

    @Override
    public CookieIdUrlResDto getCookieIdUrlCount(ViewCountsPerUserReqDto viewCountsPerUserReqDto, int memberId) {
        //        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(userCountReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = viewCountsPerUserReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = viewCountsPerUserReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(viewCountsPerUserReqDto.getApplicationToken())
        );

        Flux query = Flux.from(bucket)
                .range(startInstant, endInstant)
                .filter(restrictions)
                .groupBy(new String[]{"cookieId","currentUrl"})
                .count()
                .yield();
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        List<CookieIdUrlDto>cookieIdUrlDtoList = new ArrayList<>();
        PriorityQueue<CookieIdUrlDto> cookieIdUrlDtoPriorityQueue= new PriorityQueue<>();
        HashMap<String,PriorityQueue<ViewCountsPerUserDto>> map = new HashMap<>();
        HashMap<String,Integer> size = new HashMap<>();
        int id=0;
        for(FluxTable table:tables){
            List<FluxRecord>records= table.getRecords();

            String cookieId= records.get(0).getValueByKey("cookieId").toString();
            int count=Integer.parseInt(records.get(0).getValueByKey("_value").toString());
            String currentUrl= records.get(0).getValueByKey("currentUrl").toString();
            ViewCountsPerUserDto viewCountsPerUserDto=ViewCountsPerUserDto.create(currentUrl,count);
            if(map.containsKey(cookieId)){
                map.get(cookieId).add(viewCountsPerUserDto);
            }
            else{
                PriorityQueue<ViewCountsPerUserDto>viewCountsPerUserDtoPriorityQueue = new PriorityQueue<>();
                viewCountsPerUserDtoPriorityQueue.add(viewCountsPerUserDto);
                map.put(cookieId,viewCountsPerUserDtoPriorityQueue);
            }
            if(size.containsKey(cookieId)){
                size.replace(cookieId,size.get(cookieId)+count);

            }
            else{
                size.put(cookieId,count);
            }


        }
        map.forEach((key,value)->{
            int s= size.get(key);
            List<ViewCountsPerUserDto> viewCountsPerUserDtoList= new ArrayList<>();
            while(!value.isEmpty()){
                viewCountsPerUserDtoList.add(value.poll());
            }
            CookieIdUrlDto cookieIdUrlDto= CookieIdUrlDto.create(key,viewCountsPerUserDtoList);
            cookieIdUrlDtoPriorityQueue.add(cookieIdUrlDto.addSize(s));
        });
        while(!cookieIdUrlDtoPriorityQueue.isEmpty()){
            cookieIdUrlDtoList.add(cookieIdUrlDtoPriorityQueue.poll().addId(id++));
        }
        return CookieIdUrlResDto.create(cookieIdUrlDtoList);
    }
}
