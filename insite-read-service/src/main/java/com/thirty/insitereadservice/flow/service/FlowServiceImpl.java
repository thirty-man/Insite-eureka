package com.thirty.insitereadservice.flow.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.flow.dto.ReferrerFlowDto;
import com.thirty.insitereadservice.flow.dto.UrlFlowDto;
import com.thirty.insitereadservice.flow.dto.request.BounceReqDto;
import com.thirty.insitereadservice.flow.dto.request.ExitFlowReqDto;
import com.thirty.insitereadservice.flow.dto.request.ReferrerFlowReqDto;
import com.thirty.insitereadservice.flow.dto.request.UrlFlowReqDto;
import com.thirty.insitereadservice.flow.dto.response.BounceResDto;
import com.thirty.insitereadservice.flow.dto.response.ExitFlowResDto;
import com.thirty.insitereadservice.flow.dto.response.ReferrerFlowResDto;
import com.thirty.insitereadservice.flow.dto.response.UrlFlowResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import com.thirty.insitereadservice.flow.dto.EntryExitFlowDto;
import com.thirty.insitereadservice.flow.dto.request.EntryExitFlowReqDto;
import com.thirty.insitereadservice.flow.dto.response.EntryExitFlowResDto;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.TimeException;
import feign.FeignException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class FlowServiceImpl implements FlowService {

    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.bucket}")
    private String bucket;

    @Resource
    private final InfluxDBClient influxDBClient;

    @Override
    public EntryExitFlowResDto getEntryExitFlow(EntryExitFlowReqDto exitFlowReqDto, int memberId) {
        String token = exitFlowReqDto.getApplicationToken();
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));

        //통계 시간 설정
        Instant startInstant = exitFlowReqDto.getStartDate().toInstant(ZoneOffset.UTC);
        Instant endInstant = exitFlowReqDto.getEndDate().toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("activityId")
            .pivot(new String[]{"_value"}, new String[]{"_field"},"currentUrl");

        log.info("query = {}" ,query);

        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, Integer> exitPageRank = new HashMap<>();
        double totalUser = tables.size();
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            for (FluxRecord record: records) {
                String exitPageUrl = record.getValueByKey("applicationUrl").toString();

                if(exitPageRank.containsKey(exitPageUrl)){
                    exitPageRank.put(exitPageUrl, exitPageRank.get(exitPageUrl)+1);
                }else{
                    exitPageRank.put(exitPageUrl, 1);
                }
            }
        }

        List<EntryExitFlowDto> exitFlowDtoList = new ArrayList<>();
        PriorityQueue<EntryExitFlowDto> entryExitFlowDtoPriorityQueue = new PriorityQueue<>();
        int id = 0;

        for(String exitPageUrl: exitPageRank.keySet()){
            int count = exitPageRank.get(exitPageUrl);
            entryExitFlowDtoPriorityQueue.offer(
                EntryExitFlowDto.create(exitPageUrl, count,(totalUser == 0.0) ? 0.0:count/totalUser));
        }

        while(!entryExitFlowDtoPriorityQueue.isEmpty()){
            exitFlowDtoList.add(entryExitFlowDtoPriorityQueue.poll().addId(id++));
        }

        return EntryExitFlowResDto.create(exitFlowDtoList);
    }


    //
    @Override
    public UrlFlowResDto getUrlFlow(UrlFlowReqDto urlFlowReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(urlFlowReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = urlFlowReqDto.getStartDate().toInstant(ZoneOffset.UTC);
        Instant endInstant = urlFlowReqDto.getEndDate().toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(urlFlowReqDto.getApplicationToken()),
            Restrictions.tag("currentUrl").equal(urlFlowReqDto.getCurrentUrl())
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("beforeUrl")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();
        log.info("query = {}",query.toString());

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());

        Collections.sort(tables, new Comparator<FluxTable>() {
            @Override
            public int compare(FluxTable o1, FluxTable o2){
                return -(o1.getRecords().size()-o2.getRecords().size());
            }
        });

        List<UrlFlowDto> urlFlowDtoList = new ArrayList<>();
        PriorityQueue<UrlFlowDto> urlFlowDtoPriorityQueue = new PriorityQueue<>();
        int id = 0;

        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            String beforeUrl = records.get(0).getValueByKey("beforeUrl").toString();
            int count = records.size();

            urlFlowDtoPriorityQueue.offer(UrlFlowDto.create(beforeUrl, count));
        }

        while (!urlFlowDtoPriorityQueue.isEmpty()){
            urlFlowDtoList.add(urlFlowDtoPriorityQueue.poll().addId(id++));
        }

        return UrlFlowResDto.from(urlFlowDtoList);
    }

    @Override
    public ReferrerFlowResDto getReferrerFlow(ReferrerFlowReqDto referrerFlowReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(referrerFlowReqDto.getApplicationToken(),memberId));

        //통계 시간을 설정해주세요
        Instant startInstant = referrerFlowReqDto.getStartDate().toInstant(ZoneOffset.UTC);
        Instant endInstant = referrerFlowReqDto.getEndDate().toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(referrerFlowReqDto.getApplicationToken()),
            Restrictions.tag("currentUrl").equal(referrerFlowReqDto.getCurrentUrl())
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("referrer")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());

        Collections.sort(tables, new Comparator<FluxTable>() {
            @Override
            public int compare(FluxTable o1, FluxTable o2){
                return -(o1.getRecords().size()-o2.getRecords().size());
            }
        });

        List<ReferrerFlowDto> referrerFlowDtoList = new ArrayList<>();
        PriorityQueue<ReferrerFlowDto> referrerFlowDtoPriorityQueue = new PriorityQueue<>();
        int id = 0;

        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            String referrer = records.get(0).getValueByKey("referrer").toString();
            int count = records.size();

            referrerFlowDtoPriorityQueue.offer(ReferrerFlowDto.create(referrer,count));
        }

        while (!referrerFlowDtoPriorityQueue.isEmpty()){
            referrerFlowDtoList.add(referrerFlowDtoPriorityQueue.poll().addId(id++));
        }

        return ReferrerFlowResDto.from(referrerFlowDtoList);
    }

    @Override
    public ExitFlowResDto getExitFlow(ExitFlowReqDto exitFlowReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(exitFlowReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = exitFlowReqDto.getStartDate().toInstant(ZoneOffset.UTC);
        Instant endInstant = exitFlowReqDto.getEndDate().toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(exitFlowReqDto.getApplicationToken()),
            Restrictions.tag("currentUrl").equal(exitFlowReqDto.getCurrentUrl())
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("activityId")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();
        System.out.println(query.toString());
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());

        return ExitFlowResDto.create(tables.size());
    }

    @Override
    public BounceResDto getBounceCounts(BounceReqDto bounceReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(bounceReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = bounceReqDto.getStartDate().toInstant(ZoneOffset.UTC);
        Instant endInstant = bounceReqDto.getEndDate().toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(bounceReqDto.getApplicationToken()),
            Restrictions.tag("currentUrl").equal(bounceReqDto.getCurrentUrl())
        );

        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("activityId")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());

        int count=0;

        for(FluxTable table: tables){
            List<FluxRecord> records = table.getRecords();
            if(records.size()==1)
                ++count;
        }

        return BounceResDto.create(count);
    }
}
