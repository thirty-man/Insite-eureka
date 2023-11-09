package com.thirty.insitereadservice.flow.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.flow.dto.*;
import com.thirty.insitereadservice.flow.dto.request.*;
import com.thirty.insitereadservice.flow.dto.response.*;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.global.error.ErrorCode;
import com.thirty.insitereadservice.global.error.exception.TimeException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
        Instant startInstant = exitFlowReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = exitFlowReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

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
            .sort(new String[]{"_time"});

        log.info("query = {}" ,query);

        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, Integer> exitPageRank = new HashMap<>();
        double totalUser = tables.size();
        LocalDateTime nowMinusActiveTime = LocalDateTime.now().minusMinutes(30);
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            if (!records.isEmpty()) {
                int len = records.size()-1;
                String exitPageUrl = records.get(len).getValueByKey("currentUrl").toString();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                LocalDateTime userLastTime = LocalDateTime.parse(records.get(len).getValueByKey("_time").toString(), formatter);

                //사용자의 마지막 시간이 현재시간-30분 이후 인경우 접속중
                if(userLastTime.isAfter(nowMinusActiveTime) || userLastTime.equals(nowMinusActiveTime)){
                    continue;
                }
                
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

    @Override
    public EntryEnterFlowResDto getEntryEnterFlow(EntryEnterFlowReqDto entryEnterFlowReqDto, int memberId) {
        String token = entryEnterFlowReqDto.getApplicationToken();
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(token,memberId));

        //통계 시간 설정
        Instant startInstant = entryEnterFlowReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = entryEnterFlowReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token),
            Restrictions.tag("referrer").notEqual("null")
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("activityId");

        log.info("query = {}" ,query);

        List<FluxTable> tables = queryApi.query(query.toString());
        Map<String, Integer> enterPageRank = new HashMap<>();
        double totalUser = tables.size();
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();

            if (!records.isEmpty()) {
                String enterPageUrl = records.get(0).getValueByKey("referrer").toString();

                if(enterPageRank.containsKey(enterPageUrl)){
                    enterPageRank.put(enterPageUrl, enterPageRank.get(enterPageUrl)+1);
                }else{
                    enterPageRank.put(enterPageUrl, 1);
                }
            }
        }

        List<EntryEnterFlowDto> entryEnterFlowDtoList = new ArrayList<>();
        PriorityQueue<EntryEnterFlowDto> entryEnterFlowDtoPriorityQueue = new PriorityQueue<>();
        int id = 0;

        for(String enterPageUrl: enterPageRank.keySet()){
            int count = enterPageRank.get(enterPageUrl);
            entryEnterFlowDtoPriorityQueue.offer(
                EntryEnterFlowDto.create(enterPageUrl, count,(totalUser == 0.0) ? 0.0:count/totalUser));
        }

        while(!entryEnterFlowDtoPriorityQueue.isEmpty()){
            entryEnterFlowDtoList.add(entryEnterFlowDtoPriorityQueue.poll().addId(id++));
        }

        return EntryEnterFlowResDto.create(entryEnterFlowDtoList);
    }


    //
    @Override
    public CurrentUrlFlowResDto getUrlFlow(CurrentUrlFlowReqDto urlFlowReqDto, int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(urlFlowReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = urlFlowReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = urlFlowReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

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

        return CurrentUrlFlowResDto.from(urlFlowDtoList);
    }

    @Override
    public ReferrerFlowResDto getReferrerFlow(ReferrerFlowReqDto referrerFlowReqDto,int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(referrerFlowReqDto.getApplicationToken(),memberId));

        //통계 시간을 설정해주세요
        Instant startInstant = referrerFlowReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = referrerFlowReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(referrerFlowReqDto.getApplicationToken())
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

//        Collections.sort(tables, new Comparator<FluxTable>() {
//            @Override
//            public int compare(FluxTable o1, FluxTable o2){
//                return -(o1.getRecords().size()-o2.getRecords().size());
//            }
//        });

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
        Instant startInstant = exitFlowReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = exitFlowReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(exitFlowReqDto.getApplicationToken())
        );
        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("activityId")
                .sort(new String[]{"_time"},true)
            .yield();
        System.out.println(query.toString());
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        PriorityQueue<ExitFlowDto> exitFlowDtoPriorityQueue = new PriorityQueue<>();
        List<ExitFlowDto>exitFlowDtoList= new ArrayList<>();
        HashMap<String,ExitFlowDto> map = new HashMap<>();
        int size=0;
        int id=0;
        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            String currentUrl=records.get(0).getValueByKey("currentUrl").toString();
            ++size;
            if(map.containsKey(currentUrl)){
                map.get(currentUrl).addExitCount();
            }
            else{
                map.put(currentUrl,ExitFlowDto.create(currentUrl,1));
            }

        }
        int s=size;
        map.forEach((key,value)->{
            exitFlowDtoPriorityQueue.add(value.addSize(s));
        });
        while (!exitFlowDtoPriorityQueue.isEmpty()) {
            exitFlowDtoList.add(exitFlowDtoPriorityQueue.poll().addId(id++));
        }
        return ExitFlowResDto.create(exitFlowDtoList);
    }

    @Override
    public BounceResDto getBounceCounts(BounceReqDto bounceReqDto, int memberId) {
//        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(bounceReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = bounceReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = bounceReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(bounceReqDto.getApplicationToken())
        );

        Flux query = Flux.from(bucket)
            .range(startInstant, endInstant)
            .filter(restrictions)
            .groupBy("activityId")
                .sort(new String[] {"_time"},true)
//            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        List<BounceDto> bounceDtoList = new ArrayList<>();
        PriorityQueue<BounceDto> bounceDtoPriorityQueue = new PriorityQueue<>();

        int id=0;
        int size=0;
        HashMap<String, BounceDto> map= new HashMap<>();
        for(FluxTable table: tables){
            List<FluxRecord> records = table.getRecords();
            if(records.size()==1){
                ++size;
                String currentUrl=records.get(0).getValueByKey("currentUrl").toString();

                if(map.containsKey(currentUrl)){
                    map.get(currentUrl).addCount();
                }
                else{
                    map.put(currentUrl,BounceDto.create(currentUrl,1));
                }

            }

        }
        int s=size;
        map.forEach((key,value)->{
            bounceDtoPriorityQueue.add(value.addSize(s));
        });
        while (!bounceDtoPriorityQueue.isEmpty()){
            bounceDtoList.add(bounceDtoPriorityQueue.poll().addId(id++));
        }
        return BounceResDto.create(bounceDtoList);
    }

    @Override
    public BeforeUrlFlowResDto getBeforeUrlFlow(BeforeUrlFlowReqDto beforeUrlFlowReqDto, int memberId) {
        //        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(bounceReqDto.getApplicationToken(),memberId));

        //통계 시간 설정
        Instant startInstant = beforeUrlFlowReqDto.getStartDateTime().plusHours(9).toInstant(ZoneOffset.UTC);
        Instant endInstant = beforeUrlFlowReqDto.getEndDateTime().plusHours(33).toInstant(ZoneOffset.UTC);

        if(startInstant.isAfter(endInstant) || startInstant.equals(endInstant)){
            throw new TimeException(ErrorCode.START_TIME_BEFORE_END_TIME);
        }

        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(beforeUrlFlowReqDto.getApplicationToken())
        );
        List<BeforeUrlFlowDto> beforeUrlFlowDtoList = new ArrayList<>();
        Flux query = Flux.from(bucket)
                .range(startInstant, endInstant)
                .filter(restrictions)
                .groupBy("beforeUrl")
                .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
                .yield();

        log.info("query= {}", query);

        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        PriorityQueue<BeforeUrlFlowDto> beforeUrlFlowDtoPriorityQueue = new PriorityQueue<>();
        int id = 0;
        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            String beforeUrl = records.get(0).getValueByKey("beforeUrl").toString();
            int count = records.size();

            beforeUrlFlowDtoPriorityQueue.offer(BeforeUrlFlowDto.create(beforeUrl,count));

        }

        while (!beforeUrlFlowDtoPriorityQueue.isEmpty()){
            beforeUrlFlowDtoList.add(beforeUrlFlowDtoPriorityQueue.poll().addId(id++));
        }



        return BeforeUrlFlowResDto.create(beforeUrlFlowDtoList);
    }
}
