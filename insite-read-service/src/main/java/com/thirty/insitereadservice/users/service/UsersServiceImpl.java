package com.thirty.insitereadservice.users.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.TimeException;
import com.thirty.insitereadservice.users.dto.request.PageViewReqDto;
import com.thirty.insitereadservice.users.dto.request.UserCountReqDto;
import com.thirty.insitereadservice.users.dto.response.PageViewResDto;
import com.thirty.insitereadservice.users.dto.response.UserCountResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insitereadservice.users.dto.AbnormalDto;
import com.thirty.insitereadservice.users.dto.AbnormalUrlAndCountDto;
import com.thirty.insitereadservice.users.dto.request.AbnormalHistoryReqDto;
import com.thirty.insitereadservice.users.dto.response.AbnormalHistoryResDto;
import feign.FeignException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        String token = abnormalHistoryReqDto.getApplicationToken();
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));

        //통계 시간 설정
        Instant startInstant = abnormalHistoryReqDto.getStartDate().toInstant(ZoneOffset.UTC);
        Instant endInstant = abnormalHistoryReqDto.getEndDate().toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token),
            Restrictions.tag("requestCnt").greaterOrEqual("20")
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("currentUrl");

        log.info("query = {}" ,query);

        //해당 값 가져와 이름별로 각 숫자 저장
        List<FluxTable> tables = queryApi.query(query.toString());
        Map<LocalDateTime, AbnormalUrlAndCountDto> dateAndAbnormalDtoUrlAndCountMap = new HashMap<>();
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record: records) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime date = LocalDateTime.parse(record.getValueByKey("_time").toString(), formatter);

                String currentUrl = record.getValueByKey("currentUrl").toString();

                if(dateAndAbnormalDtoUrlAndCountMap.containsKey(date)){
                    AbnormalUrlAndCountDto abnormalUrlAndCountDto = dateAndAbnormalDtoUrlAndCountMap.get(date);
                    abnormalUrlAndCountDto.addCount();

                    dateAndAbnormalDtoUrlAndCountMap.put(date, abnormalUrlAndCountDto);
                }else{
                    dateAndAbnormalDtoUrlAndCountMap.put(date, AbnormalUrlAndCountDto.create(currentUrl,1));
                }
            }
        }

        List<AbnormalDto> abnormalDtoList = new ArrayList<>();
        PriorityQueue<AbnormalDto> abnormalDtoPriorityQueue = new PriorityQueue<>();
        int id = 0 ;

        for(LocalDateTime date: dateAndAbnormalDtoUrlAndCountMap.keySet()){
            AbnormalUrlAndCountDto abnormalUrlAndCountDto = dateAndAbnormalDtoUrlAndCountMap.get(date);

            abnormalDtoPriorityQueue.offer(
                AbnormalDto.create(date, abnormalUrlAndCountDto.getUrl(), abnormalUrlAndCountDto.getCount()));
        }

        while (!abnormalDtoPriorityQueue.isEmpty()){
            abnormalDtoList.add(abnormalDtoPriorityQueue.poll().addId(id++));
        }

        return AbnormalHistoryResDto.create(abnormalDtoList);
    }

    //
    @Override
    public PageViewResDto getPageView(PageViewReqDto pageViewReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(pageViewReqDto.getApplicationToken(), memberId));

        //통계 시간 설정
        Instant startInstant = pageViewReqDto.getStartDate().toInstant(ZoneOffset.UTC);
        Instant endInstant = pageViewReqDto.getEndDate().toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(pageViewReqDto.getApplicationToken()),
            Restrictions.tag("currentUrl").equal(pageViewReqDto.getCurrentUrl())
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("applicationToken")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        int count=0;

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            count+= records.size();
        }

        return PageViewResDto.create(count);
    }

    @Override
    public UserCountResDto getUserCount(UserCountReqDto userCountReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(userCountReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = userCountReqDto.getStartDate().toInstant(ZoneOffset.UTC);
        Instant endInstant = userCountReqDto.getEndDate().toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal("\""+userCountReqDto.getApplicationToken()+"\"")
        );

        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("cookieId")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());

        return UserCountResDto.create(tables.size());
    }
}
