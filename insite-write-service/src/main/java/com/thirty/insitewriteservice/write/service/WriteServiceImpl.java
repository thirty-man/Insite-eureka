package com.thirty.insitewriteservice.write.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitewriteservice.global.service.InfluxDBService;
import com.thirty.insitewriteservice.write.dto.DataReqDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WriteServiceImpl implements WriteService {
    @Resource
    private InfluxDBClient influxDBClient;
    @Value("${influxdb.org}")
    private String org;
    @Value("${influxdb.bucket}")
    private String bucket;

    private final InfluxDBService influxDBService;

    @Override
    public void writeData(DataReqDto dataReqDto) {

        // applicationToken 과 applicationUrl 유효성 검증
        if(!verifyApplicationToken(dataReqDto.getApplicationToken(), dataReqDto.getApplicationUrl())) return;

        // activityId 및 abnormal requestCnt 갱신
        String[] activityId_requestCnt = getActivityIdAndRequestId(dataReqDto.getCookieId());
        dataReqDto.updateActivityId(activityId_requestCnt[0]);
        dataReqDto.updateRequestCnt(activityId_requestCnt[1]);

        // 데이터 쓰기
        influxDBService.writeDataToData(dataReqDto);
    }

    public boolean verifyApplicationToken(String ApplicationToken, String ApplicationUrl){

        return true;
    }

    public String generateNewActivityId() {
        return UUID.randomUUID().toString();
    }
    public String[] getActivityIdAndRequestId(String cookieId) {
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("cookieId").equal(cookieId)
        );
        Flux query = Flux.from("insite")
                .range(-30L, ChronoUnit.MINUTES)
                .filter(restrictions)
                .groupBy("_time")
                .sort(new String[] {"_time"}, false);

        List<FluxTable> tables = queryApi.query(query.toString());
        String[] activityId_requestId;

        if(tables.size() == 0) return new String[] {generateNewActivityId(), "1"};
        List<FluxRecord> records = tables.get(tables.size() - 1).getRecords();
        if(tables.isEmpty() || records.isEmpty()){
            activityId_requestId = new String[] {generateNewActivityId(), "1"};
            return activityId_requestId;
        }

        FluxRecord lastActivity = records.get(0);
        Instant lastActivityTime = Instant.parse(lastActivity.getValueByKey("_time").toString());
        Duration duration = Duration.between(lastActivityTime, Instant.now());

        if (duration.getSeconds() < 5) { // 5초 내 다시 request 발생할 때
            int currentRequestCnt = Integer.parseInt(lastActivity.getValueByKey("requestCnt").toString());
            activityId_requestId = new String[] {lastActivity.getValueByKey("activityId").toString(), String.valueOf(currentRequestCnt + 1)};
        } else {
            activityId_requestId = new String[] {lastActivity.getValueByKey("activityId").toString(), "1"};
        }
        return activityId_requestId;
    }
}

