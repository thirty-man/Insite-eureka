package com.thirty.insitereadservice.currenturl.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.currenturl.dto.CurrentUrlDto;
import com.thirty.insitereadservice.currenturl.dto.req.CurrentUrlListReqDto;
import com.thirty.insitereadservice.currenturl.dto.res.CurrentUrlListResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.TimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrentUrlServiceImpl implements CurrentUrlService{
    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Resource
    private InfluxDBClient influxDBClient;
    @Override
    public CurrentUrlListResDto getCurrentUrlList(CurrentUrlListReqDto currentUrlListReqDto, int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(activeUserReqDto.getApplicationToken(),memberId));
        //범위 시간 지정
        Instant startInstant = currentUrlListReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = currentUrlListReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant)  || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(currentUrlListReqDto.getApplicationToken())
        );
        Flux query = Flux.from(bucket)
                .range(startInstant, endInstant)
                .filter(restrictions)
                .groupBy("currentUrl")
                        .count();

        log.info("query ={}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        PriorityQueue<CurrentUrlDto>currentUrlDtoPriorityQueue = new PriorityQueue<>();
        for(FluxTable table : tables){
            List<FluxRecord>records=table.getRecords();
            String currentUrl = records.get(0).getValueByKey("currentUrl").toString();
            int count = Integer.parseInt(records.get(0).getValueByKey("_value").toString());
            currentUrlDtoPriorityQueue.add(CurrentUrlDto.create(currentUrl,count));
        }
        int id=0;
        List<CurrentUrlDto>currentUrlDtoList = new ArrayList<>();
        while(!currentUrlDtoPriorityQueue.isEmpty()){
            currentUrlDtoList.add(currentUrlDtoPriorityQueue.poll().addId(id++));
        }
        return CurrentUrlListResDto.create(currentUrlDtoList);
    }
}
