package com.thirty.insiterealtimereadservice.buttons.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insiterealtimereadservice.buttons.dto.CountPerUserDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.CountPerUserResDto;
import com.thirty.insiterealtimereadservice.feignclient.MemberServiceClient;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
public class ButtonServiceImpl implements ButtonService{
    private final MemberServiceClient memberServiceClient;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public CountPerUserResDto countPerUser(int memberId, String token) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));

        QueryApi queryApi = influxDBClient.getQueryApi();

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux query = Flux.from("insite")
            .range(-30L, ChronoUnit.MINUTES)
            .filter(restrictions)
            .groupBy(new String[]{"name","cookieId"})
            .count();
        log.info("query={}",query);
        // 쿼리 결과 매핑
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

        // 결과 출력
        List<CountPerUserDto> countPerUserDtoList = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> entry : nameCookieIdCountMap.entrySet()) {
            String name = entry.getKey();

            Map<String, Integer> cookieIdCounts = entry.getValue();
            double size = cookieIdCounts.size(); // 맵의 크기를 가져옴

            int sum = 0;
            for (Map.Entry<String, Integer> cookieIdEntry : cookieIdCounts.entrySet()) {
                sum += cookieIdEntry.getValue();
            }

            log.info("name={}", name);
            log.info("sum={}", sum);
            log.info("size={}", size);

            double average = (size == 0) ? 0.0 : sum / size;
            countPerUserDtoList.add(CountPerUserDto.create(name, sum, average));
        }
        return CountPerUserResDto.create(countPerUserDtoList);
    }
}
