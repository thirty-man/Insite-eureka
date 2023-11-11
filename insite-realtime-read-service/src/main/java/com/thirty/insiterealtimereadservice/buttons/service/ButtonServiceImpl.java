package com.thirty.insiterealtimereadservice.buttons.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.thirty.insiterealtimereadservice.buttons.dto.CountPerUserDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.ButtonAbnormalResDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.ClickCountPerUserResDto;
import com.thirty.insiterealtimereadservice.feignclient.MemberServiceClient;
import com.thirty.insiterealtimereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insiterealtimereadservice.global.builder.InfluxQueryBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ButtonServiceImpl implements ButtonService{

    private final MemberServiceClient memberServiceClient;
    private final InfluxQueryBuilder influxQueryBuilder;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ClickCountPerUserResDto countPerUser(int memberId, String applicationToken) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken,memberId));

        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux query = influxQueryBuilder.buttonClickCountPerUser(applicationToken);

        Map<String, Map<String, Integer>> nameCookieIdCountMap = getNameAndClickCountsWithCookieIdMap(
            queryApi, query);

        return ClickCountPerUserResDto.create(getCountPerUserDtoList(nameCookieIdCountMap));
    }

    @Override
    public List<ButtonAbnormalResDto> getButtonAbnormal(int memberId, String applicationToken) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken,memberId));

        QueryApi queryApi = influxDBClient.getQueryApi();
        String query = influxQueryBuilder.buttonAbnormal(applicationToken);

        List<ButtonAbnormalResDto> buttonAbnormalResDtoList = getButtonAbnormalResDtoList(
            queryApi, query);
        return buttonAbnormalResDtoList;
    }

    private List<CountPerUserDto> getCountPerUserDtoList(Map<String, Map<String, Integer>> nameCookieIdCountMap) {
        List<CountPerUserDto> countPerUserDtoList = new ArrayList<>();
        PriorityQueue<CountPerUserDto> ranking = new PriorityQueue();
        for (Map.Entry<String, Map<String, Integer>> entry : nameCookieIdCountMap.entrySet()) {
            String name = entry.getKey();

            Map<String, Integer> cookieIdCounts = entry.getValue();
            double size = cookieIdCounts.size(); // 맵의 크기를 가져옴

            int sum = 0;
            for (Map.Entry<String, Integer> cookieIdEntry : cookieIdCounts.entrySet()) {
                sum += cookieIdEntry.getValue();
            }

            double average = (size == 0) ? 0.0 : sum / size;
            ranking.offer(CountPerUserDto.create(name, sum, average));
        }

        int id = 0;
        while (!ranking.isEmpty()){
            countPerUserDtoList.add(ranking.poll().addId(id++));
        }
        return countPerUserDtoList;
    }

    @NotNull
    private Map<String, Map<String, Integer>> getNameAndClickCountsWithCookieIdMap(QueryApi queryApi, Flux query) {
        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, Map<String, Integer>> nameCookieIdCountMap = new HashMap<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                String name = record.getValueByKey("name").toString();
                String cookieId = record.getValueByKey("cookieId").toString();
                String count = record.getValueByKey("_value").toString();
                int cookieCount = Integer.valueOf(count);

                // 1차 그룹화: 이름(name)으로 그룹화
                nameCookieIdCountMap.computeIfAbsent(name, k -> new HashMap<>());
                // 2차 그룹화: 쿠키 아이디(cookieId)로 그룹화
                nameCookieIdCountMap.get(name)
                    .compute(cookieId, (k, v) -> (v == null) ? cookieCount : v+cookieCount);
            }
        }
        return nameCookieIdCountMap;
    }

    @NotNull
    private List<ButtonAbnormalResDto> getButtonAbnormalResDtoList(QueryApi queryApi, String query) {
        List<FluxTable> tables = queryApi.query(query);
        List<ButtonAbnormalResDto> buttonAbnormalResDtoList = new ArrayList<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record : records) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime currentDateTime = LocalDateTime.parse(record.getValueByKey("_time").toString(), formatter);

                String buttonName = record.getValueByKey("name").toString();
                String cookieId = record.getValueByKey("cookieId").toString();
                String currentUrl = record.getValueByKey("currentUrl").toString();
                String stringValueOfRequestCnt = record.getValueByKey("requestCnt").toString();
                int requestCnt = Integer.valueOf(stringValueOfRequestCnt);

                buttonAbnormalResDtoList.add(ButtonAbnormalResDto.create(cookieId, buttonName, currentDateTime,currentUrl,requestCnt));
            }
        }
        return buttonAbnormalResDtoList;
    }
}
