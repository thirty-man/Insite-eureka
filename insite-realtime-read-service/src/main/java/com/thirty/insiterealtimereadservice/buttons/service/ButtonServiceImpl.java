package com.thirty.insiterealtimereadservice.buttons.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insiterealtimereadservice.buttons.dto.CountDto;
import com.thirty.insiterealtimereadservice.buttons.dto.CountPerUserDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.CountPerUserResDto;
import com.thirty.insiterealtimereadservice.buttons.dto.response.CountResDto;
import com.thirty.insiterealtimereadservice.buttons.measurement.Button;
import com.thirty.insiterealtimereadservice.feignclient.MemberServiceClient;
import com.thirty.insiterealtimereadservice.feignclient.dto.request.MemberValidReqDto;
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
    public CountResDto count(int memberId, String token) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));

        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("button"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux query = Flux.from("insite")
            .range(-30L, ChronoUnit.MINUTES)
            .filter(restrictions)
            .groupBy("name")
            .count();

        log.info("query = {}" ,query);

        //해당 값 가져와 이름별로 각 숫자 저장
        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, Integer> nameCounts = new HashMap<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record : records) {
                String name = record.getValueByKey("name").toString();
                String countStringValue = record.getValueByKey("_value").toString();
                int count = Integer.valueOf(countStringValue);

                // Map에 이름(name)과 해당 이름의 카운트 값을 저장
                nameCounts.put(name, count);
            }
        }

        //response 형식으로 변환
        List<CountDto> countDtoList = new ArrayList<>();
        for(String name : nameCounts.keySet()){
                countDtoList.add(CountDto.create(name, nameCounts.get(name)));
        }

        return CountResDto.create(countDtoList);
    }

    @Override
    public CountPerUserResDto countPerUser(int memberId, String token) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));

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
            int size = cookieIdCounts.size(); // 맵의 크기를 가져옴

            Double sum = 0.0;
            for (Map.Entry<String, Integer> cookieIdEntry : cookieIdCounts.entrySet()) {
                sum += cookieIdEntry.getValue();
            }

            log.info("name={}", name);
            log.info("sum={}", sum);
            log.info("size={}", size);

            Double average = (size == 0) ? 0.0 : sum / size;
            countPerUserDtoList.add(CountPerUserDto.create(name, average));
        }
        return CountPerUserResDto.create(countPerUserDtoList);
    }

    private List<Button> readAll(){
        QueryApi queryApi = influxDBClient.getQueryApi();
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder
            .append("from(bucket: \"insite\")")
            .append(" |> range(start:0)")
            .append(" |> filter(fn: (r) => r._measurement == \"button\")");

        List<FluxTable> tables = queryApi.query(queryBuilder.toString());
        List<Button> buttonList = new ArrayList<>();

        for (FluxTable fluxTable : tables) {
			List<FluxRecord> records = fluxTable.getRecords();
			for (FluxRecord record : records) {
				String cookieId = record.getValueByKey("cookieId").toString();
				String currentUrl = record.getValueByKey("_value").toString();
				String name = record.getValueByKey("name").toString();
                String serviceToken = record.getValueByKey("serviceToken").toString();

                //버튼 리스트에 추가
               buttonList.add(Button.create(name, currentUrl, cookieId, serviceToken));
			}
		}
        return buttonList;
    }
}
