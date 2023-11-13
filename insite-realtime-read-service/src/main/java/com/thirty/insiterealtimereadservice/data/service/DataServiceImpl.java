package com.thirty.insiterealtimereadservice.data.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
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
        double sum = 0.0;

        Map<String, Double> referrerWithCount = getReferrerWithCountAndPlusSum(sum, tables);
        return ReferrerResDto.create(getReferrerDtoList(referrerWithCount.get("sum"), referrerWithCount));
    }

    @Override
    public UserCountResDto getUserCount(int memberId, String applicationToken) {
        //멤버 및 application 유효성검사
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken, memberId));

        Flux query = influxQueryBuilder.getUserCount(applicationToken);
        QueryApi queryApi = influxDBClient.getQueryApi();

        List<FluxTable> tables = queryApi.query(query.toString());
        double sum = 0.0;

        for(FluxTable table : tables){
            sum+=table.getRecords().size();
        }

        Map<String, CountWithResponseTime> urlWithCountAndResponseTime = getCurrentUrlWithCountWithResponseTime(tables);
        return UserCountResDto.create(getUserCountDtoList(sum, urlWithCountAndResponseTime));
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

    private Map<String, Double> getReferrerWithCountAndPlusSum(double sum , List<FluxTable> tables){

        Map<String, Double> referrerWithCount= new HashMap<>();

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                String referrer = record.getValueByKey("referrer").toString();

                if(referrer.equals("null")){
                    continue;
                }

                String count = record.getValueByKey("_value").toString();
                double referrerCount = Double.valueOf(count);
                sum += referrerCount;
                referrerWithCount.put(referrer, referrerCount);
            }
        }
        referrerWithCount.put("sum", sum);
        return referrerWithCount;
    }

    @NotNull
    private List<ReferrerDto> getReferrerDtoList(double sum, Map<String, Double> referrerWithCount) {
        List<ReferrerDto> referrerDtoList = new ArrayList<>();
        PriorityQueue<ReferrerDto> referrerDtoPriorityQueue = new PriorityQueue<>();

        for(String url : referrerWithCount.keySet()){

            if(url.equals("sum")){
                continue;
            }

            double curCount = referrerWithCount.get(url);

            referrerDtoPriorityQueue.offer(ReferrerDto.create(
                url,
                (int)curCount,
                (sum == 0) ? 0.0 : curCount/ sum
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
            List<FluxRecord> records = fluxTable.getRecords();
            String url = "";
            int sumResponseTime = 0;

            for (FluxRecord record : records) {
                url = record.getValueByKey("currentUrl").toString();
                String stringValueOfResponseTime = record.getValueByKey("responseTime").toString();
                sumResponseTime += Integer.valueOf(stringValueOfResponseTime);

            }
            if(!url.equals("")){
                int size = records.size();
                urlWithCountAndResponseTime.put(url, CountWithResponseTime.create(size, sumResponseTime/size));
            }
        }

        return urlWithCountAndResponseTime;
    }

    @NotNull
    private List<UserCountDto> getUserCountDtoList(double sum,
        Map<String, CountWithResponseTime> urlWithCountAndResponseTime) {
        PriorityQueue<UserCountDto> userCountDtoPriorityQueue = new PriorityQueue<>();
        List<UserCountDto> userCountDtoList = new ArrayList<>();

        for(String url : urlWithCountAndResponseTime.keySet()){
            CountWithResponseTime countWithResponseTime = urlWithCountAndResponseTime.get(url);

            userCountDtoPriorityQueue.offer(UserCountDto.create(
                url,
                countWithResponseTime.getCount(),
                (sum == 0)? 0.0 : countWithResponseTime.getCount()/ sum,
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

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime time = LocalDateTime.parse(record.getValueByKey("_time").toString(), formatter);

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
