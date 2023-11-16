package com.thirty.insiterealtimereadservice.data.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.thirty.insiterealtimereadservice.data.dto.AbnormalDto;
import com.thirty.insiterealtimereadservice.data.dto.CountWithResponseTime;
import com.thirty.insiterealtimereadservice.data.dto.ReferrerDto;
import com.thirty.insiterealtimereadservice.data.dto.UserCountDto;
import com.thirty.insiterealtimereadservice.data.dto.response.AbnormalResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.ReferrerResDto;
import com.thirty.insiterealtimereadservice.data.dto.response.UserCountResDto;
import com.thirty.insiterealtimereadservice.feignclient.MemberServiceClient;
import com.thirty.insiterealtimereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insiterealtimereadservice.global.builder.InfluxQueryBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService{

    private final MemberServiceClient memberServiceClient;
    private final InfluxQueryBuilder influxQueryBuilder;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ReferrerResDto getReferrer(int memberId, String applicationToken) {
        //멤버 및 application 유효성검사
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken, memberId));

        QueryApi queryApi = influxDBClient.getQueryApi();
        Flux query = influxQueryBuilder.getReferrer(applicationToken);

        List<FluxTable> tables = queryApi.query(query.toString());

        Map<String, Integer> referrerWithCount = getReferrerWithCountAndPlusSum(tables);
        return ReferrerResDto.create(getReferrerDtoList(referrerWithCount.get("sum"), referrerWithCount));
    }

    @Override
    public UserCountResDto getUserCount(int memberId, String applicationToken) {
        //멤버 및 application 유효성검사
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken, memberId));

        Flux query = influxQueryBuilder.getUserCount(applicationToken);
        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(query.toString());

        int sum = 0;
        for(FluxTable table : tables){
            sum += table.getRecords().size();
        }

        Map<String, CountWithResponseTime> urlWithCountAndResponseTime = getCurrentUrlWithCountWithResponseTime(tables);
        return UserCountResDto.create(getUserCountDtoList(sum , urlWithCountAndResponseTime));
    }

    @Override
    public AbnormalResDto getAbnormal(int memberId, String applicationToken) {
        //멤버 및 application 유효성검사
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken, memberId));

        QueryApi queryApi = influxDBClient.getQueryApi();
        String query = influxQueryBuilder.getDataAbnormal(applicationToken);

        List<FluxTable> tables = queryApi.query(query);
        return AbnormalResDto.create(getAbnormalDtoList(tables));
    }

    private Map<String, Integer> getReferrerWithCountAndPlusSum(List<FluxTable> tables){

        Map<String, Integer> referrerWithCount= new HashMap<>();
        int sum = 0;
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            Set<String> cookieIdSet = new HashSet<>();
            String referrer = records.get(0).getValueByKey("referrer").toString();
            for (FluxRecord record : records) {
                cookieIdSet.add(record.getValueByKey("cookieId").toString());
            }
            int userCount = cookieIdSet.size();
            sum+=userCount;
            referrerWithCount.put(referrer, userCount);
        }
        referrerWithCount.put("sum", sum);
        return referrerWithCount;
    }

    @NotNull
    private List<ReferrerDto> getReferrerDtoList(int sum, Map<String, Integer> referrerWithCount) {
        List<ReferrerDto> referrerDtoList = new ArrayList<>();
        PriorityQueue<ReferrerDto> referrerDtoPriorityQueue = new PriorityQueue<>();

        for(String url : referrerWithCount.keySet()){

            if(url.equals("sum")){
                continue;
            }

            int curCount = referrerWithCount.get(url);

            referrerDtoPriorityQueue.offer(ReferrerDto.create(
                url,
                curCount,
                (sum == 0) ? 0.0 : curCount/ (double)sum
            ));
        }

        int id = 0;
        while (!referrerDtoPriorityQueue.isEmpty()){
            referrerDtoList.add(referrerDtoPriorityQueue.poll().addId(id++));
        }
        return referrerDtoList;
    }

    private Map<String, CountWithResponseTime> getCurrentUrlWithCountWithResponseTime(List<FluxTable> tables){
        Map<String, CountWithResponseTime> urlWithCountAndResponseTime = new HashMap<>();

        for (FluxTable fluxTable : tables) {
            Set<String> userCount = new HashSet<>();
            List<FluxRecord> records = fluxTable.getRecords();

            String url = records.get(0).getValueByKey("currentUrl").toString();
            double sumResponseTime = 0;
            for (FluxRecord record : records) {
                String stringValueOfResponseTime = record.getValueByKey("responseTime").toString();
                userCount.add(record.getValueByKey("cookieId").toString());

                sumResponseTime += Double.valueOf(stringValueOfResponseTime);
            }

            int size = records.size();
            urlWithCountAndResponseTime.put(url, CountWithResponseTime.create(size, userCount.size(), sumResponseTime/size));
        }

        return urlWithCountAndResponseTime;
    }

    @NotNull
    private List<UserCountDto> getUserCountDtoList(int sum, Map<String, CountWithResponseTime> urlWithCountAndResponseTime) {
        PriorityQueue<UserCountDto> userCountDtoPriorityQueue = new PriorityQueue<>();
        List<UserCountDto> userCountDtoList = new ArrayList<>();

        for(String url : urlWithCountAndResponseTime.keySet()){
            CountWithResponseTime countWithResponseTime = urlWithCountAndResponseTime.get(url);

            userCountDtoPriorityQueue.offer(UserCountDto.create(
                url,
                countWithResponseTime.getViewCount(),
                countWithResponseTime.getUserCount(),
                sum == 0 ? 0.0 : countWithResponseTime.getViewCount()/(double) sum,
                countWithResponseTime.getResponseTime()
            ));
        }

        int id = 0;
        while(!userCountDtoPriorityQueue.isEmpty()){
            userCountDtoList.add(userCountDtoPriorityQueue.poll().addId(id++));
        }
        return userCountDtoList;
    }

    @NotNull
    private List<AbnormalDto> getAbnormalDtoList(List<FluxTable> tables) {
        List<AbnormalDto> abnormalDtoList = new ArrayList<>();
        int id = 0;
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                String cookieId = record.getValueByKey("cookieId").toString();

                DateTimeFormatter millisecondFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String stringValueOfTime = record.getValueByKey("_time").toString();
                LocalDateTime time = LocalDateTime.parse(stringValueOfTime, stringValueOfTime.length() < 24 ? formatter : millisecondFormatter);

                String currentUrl = record.getValueByKey("currentUrl").toString();
                String language = record.getValueByKey("language").toString();
                String osId = record.getValueByKey("osId").toString();
                String stringValueOfRequestCnt = record.getValueByKey("requestCnt").toString();
                int requestCnt = Integer.valueOf(stringValueOfRequestCnt);

                abnormalDtoList.add(AbnormalDto.create(cookieId,time,currentUrl,language,requestCnt,osId).addId(id++));
            }
        }
        return abnormalDtoList;
    }
}
