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
public class DataServiceImpl implements DataService{

    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Resource
    private InfluxDBClient influxDBClient;

    @Override
    public ReferrerResDto getReferrer(int memberId, String token) {
        //멤버 및 application 유효성검사
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token, memberId));
        Instant now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        Instant beforeThirtyMinutes = LocalDateTime.now().minusMinutes(30).toInstant(ZoneOffset.UTC);
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux query = Flux.from(bucket)
            .range(beforeThirtyMinutes, now)
            .filter(restrictions)
            .groupBy("beforeUrl")
            .count();

        log.info("query= {}",query);

        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, Double> map= new HashMap<>();
        double sum = 0.0;
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            for (FluxRecord record : records) {
                String url = record.getValueByKey("beforeUrl").toString();
                String count = record.getValueByKey("_value").toString();
                Double urlCount = Double.valueOf(count);
                sum += urlCount;

                map.put(url, urlCount);
            }
        }
        List<ReferrerDto> referrerDtoList = new ArrayList<>();
        PriorityQueue<ReferrerDto> referrerDtoPriorityQueue = new PriorityQueue<>();

        for(String url : map.keySet()){
            double curCount = map.get(url);

            referrerDtoPriorityQueue.offer(ReferrerDto.create(
                url,
                (int)curCount,
                (sum == 0) ? 0.0 : curCount/sum
            ));
        }

        int id = 0;
        while (!referrerDtoPriorityQueue.isEmpty()){
            referrerDtoList.add(referrerDtoPriorityQueue.poll().addId(id++));
        }
        return ReferrerResDto.create(referrerDtoList);
    }

    @Override
    public UserCountResDto getUserCount(int memberId, String token) {
        //멤버 및 application 유효성검사
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token, memberId));

        Instant now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        Instant beforeThirtyMinutes = LocalDateTime.now().minusMinutes(30).toInstant(ZoneOffset.UTC);

        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux query = Flux.from(bucket)
            .range(beforeThirtyMinutes,now)
            .filter(restrictions)
            .groupBy("currentUrl");
//            .count();

        log.info("query= {}",query);

        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, CountWithResponseTime> urlWithCountAndResponseTime= new HashMap<>();
        double sum = 0.0;

        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            String url = "";
            int sumResponseTime = 0;
            sum += records.size();

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

        PriorityQueue<UserCountDto> userCountDtoPriorityQueue = new PriorityQueue<>();
        List<UserCountDto> userCountDtoList = new ArrayList<>();

        for(String url : urlWithCountAndResponseTime.keySet()){
            CountWithResponseTime countWithResponseTime = urlWithCountAndResponseTime.get(url);

            userCountDtoPriorityQueue.offer(UserCountDto.create(
                url,
                countWithResponseTime.getCount(),
                (sum == 0)? 0.0 : countWithResponseTime.getCount()/sum,
                countWithResponseTime.getResponseTime()
            ));
        }

        int id = 0;
        while(!userCountDtoPriorityQueue.isEmpty()){
            userCountDtoList.add(userCountDtoPriorityQueue.poll().addId(id++));
        }

        return UserCountResDto.create(userCountDtoList);
    }

    @Override
    public AbnormalResDto getAbnormal(int memberId, String applicationToken) {
        //멤버 및 application 유효성검사
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(applicationToken, memberId));

        Instant now = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        Instant beforeThirtyMinutes = LocalDateTime.now().minusMinutes(30).toInstant(ZoneOffset.UTC);

        QueryApi queryApi = influxDBClient.getQueryApi();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("from(bucket: \"").append(bucket).append("\")\n");
        queryBuilder.append("  |> range(start: ").append(beforeThirtyMinutes).append(", stop:").append(now).append(")\n");
        queryBuilder.append("  |> filter(fn: (r) => r._measurement == \"data\" and r.applicationToken == \"")
            .append(applicationToken).append("\" and float(v: r.requestCnt) >= 10)\n");
        queryBuilder.append("  |> group(columns:[\"\"])\n");
        queryBuilder.append("  |> sort(columns: [\"_time\"])");

        log.info("query={}",queryBuilder);

        List<FluxTable> tables = queryApi.query(queryBuilder.toString());
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

        return AbnormalResDto.create(abnormalDtoList);
    }
}
