package com.thirty.insiterealtimereadservice.common.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.thirty.insiterealtimereadservice.common.dto.response.CommonResDto;
import com.thirty.insiterealtimereadservice.feignclient.MemberServiceClient;
import com.thirty.insiterealtimereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insiterealtimereadservice.global.builder.InfluxQueryBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService{

    private final MemberServiceClient memberServiceClient;
    private final InfluxQueryBuilder influxQueryBuilder;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public CommonResDto getCommonInfo(String applicationToken, int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken,memberId));

        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux query = influxQueryBuilder.getCommonInfo(applicationToken);

        List<FluxTable> tables = queryApi.query(query.toString());

        int everyActiveUserCount = tables.size();
        if(everyActiveUserCount == 0){
            return CommonResDto.create(everyActiveUserCount,0,0.0,0);
        }

        Map<String ,Integer> CommonResMap = calculateQueryRes(tables, 0, 0, 0);

        return CommonResDto.create(
            everyActiveUserCount,
            CommonResMap.get("everyInflowUserCount"),
            CommonResMap.get("everyViewCount")/everyActiveUserCount,
            CommonResMap.get("everyAbnormalCount")
        );
    }

    private Map<String, Integer> calculateQueryRes(List<FluxTable> tables, int everyViewCount, int everyInflowUserCount, int everyAbnormalCount){
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            everyViewCount += records.size();

            for(FluxRecord record : records){

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

        Map<String, Integer> CommonDtoMap = new HashMap<>();
        CommonDtoMap.put("everyViewCount",everyViewCount);
        CommonDtoMap.put("everyInflowUserCount",everyInflowUserCount);
        CommonDtoMap.put("everyAbnormalCount",everyAbnormalCount);

        return CommonDtoMap;
    }
}
