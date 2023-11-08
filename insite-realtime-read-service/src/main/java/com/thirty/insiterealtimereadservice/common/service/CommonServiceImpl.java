package com.thirty.insiterealtimereadservice.common.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insiterealtimereadservice.common.dto.request.CommonReqDto;
import com.thirty.insiterealtimereadservice.common.dto.response.CommonResDto;
import com.thirty.insiterealtimereadservice.feignclient.MemberServiceClient;
import com.thirty.insiterealtimereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insiterealtimereadservice.global.error.ErrorCode;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService{

    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public CommonResDto getCommonInfo(CommonReqDto commonReqDto, int memberId) {
        String token = commonReqDto.getApplicationToken();
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));

        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux query = Flux.from(bucket)
            .range(-30L, ChronoUnit.MINUTES)
            .filter(restrictions)
            .groupBy("activityId");

        log.info("query = {}" ,query);

        List<FluxTable> tables = queryApi.query(query.toString());
        int everyActiveUserCount = tables.size();
        if(everyActiveUserCount == 0){
            return CommonResDto.create(0,0,0.0,0);
        }
        int everyInflowUserCount = 0;
        double everyViewCount = 0.0;
        int everyAbnormalCount = 0;

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for(FluxRecord record : records){
                everyViewCount++;

                //referrer 가 null이 아닌 레코드 수
                if(!record.getValueByKey("referrer").equals("null")){
                    everyInflowUserCount++;
                }

                String stringValueOfRequestCnt = record.getValueByKey("requestCnt").toString();
                if(Integer.valueOf(stringValueOfRequestCnt) >= 10){
                    everyAbnormalCount++;
                }
            }
        }
        return CommonResDto.create(everyActiveUserCount, everyInflowUserCount, everyViewCount/everyActiveUserCount, everyAbnormalCount);
    }
}
