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
import feign.FeignException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        try{
            memberServiceClient.validationMemberAndApplication(
                MemberValidReqDto.create(token,memberId));
        }catch (FeignException fe){
            log.error(fe.getMessage());
        }
        //쿼리 생성
        QueryApi queryApi = influxDBClient.getQueryApi();
        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal(token)
        );
        Flux query = Flux.from(bucket)
            .range(0L)
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
        for(String exitPageUrl: exitPageRank.keySet()){
            int count = exitPageRank.get(exitPageUrl);
            exitFlowDtoList.add(
                EntryExitFlowDto.create(exitPageUrl, count,(totalUser == 0.0) ? 0.0:count/totalUser));
        }
        return EntryExitFlowResDto.create(exitFlowDtoList);
    }


    //
    @Override
    public UrlFlowResDto getUrlFlow(UrlFlowReqDto urlFlowReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(urlFlowReqDto.getApplicationToken(),memberId));

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal("\""+urlFlowReqDto.getApplicationToken()+"\""),
            Restrictions.tag("currentUrl").equal("\""+urlFlowReqDto.getCurrentUrl()+"\"")
        );
        Flux query = Flux.from(bucket)
            .range(0L)
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
        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            urlFlowDtoList.add(UrlFlowDto.builder().beforeUrl(records.get(0).getValueByKey("beforeUrl").toString())
                .count(records.size()).build());
        }

        return UrlFlowResDto.from(urlFlowDtoList);
    }

    @Override
    public ReferrerFlowResDto getReferrerFlow(ReferrerFlowReqDto referrerFlowReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(referrerFlowReqDto.getApplicationToken(),memberId));

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal("\""+referrerFlowReqDto.getApplicationToken()+"\""),
            Restrictions.tag("currentUrl").equal("\""+referrerFlowReqDto.getCurrentUrl()+"\"")
        );
        Flux query = Flux.from(bucket)
            .range(0L)
            .filter(restrictions)
            .groupBy("referrer")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();
        System.out.println(query.toString());
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        Collections.sort(tables, new Comparator<FluxTable>() {
            @Override
            public int compare(FluxTable o1, FluxTable o2){
                return -(o1.getRecords().size()-o2.getRecords().size());
            }
        });
        List<ReferrerFlowDto> referrerFlowDtoList = new ArrayList<>();
        for(FluxTable fluxTable:tables){
            List<FluxRecord> records = fluxTable.getRecords();
            referrerFlowDtoList.add(ReferrerFlowDto.builder().referrer(records.get(0).getValueByKey("referrer").toString())
                .count(records.size()).build());
        }

        return ReferrerFlowResDto.from(referrerFlowDtoList);
    }

    @Override
    public ExitFlowResDto getExitFlow(
        ExitFlowReqDto exitFlowReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(exitFlowReqDto.getApplicationToken(),memberId));

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal("\""+exitFlowReqDto.getApplicationToken()+"\""),
            Restrictions.tag("currentUrl").equal("\""+exitFlowReqDto.getCurrentUrl()+"\"")
        );
        Flux query = Flux.from(bucket)
            .range(0L)
            .filter(restrictions)
            .groupBy("activityId")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();
        System.out.println(query.toString());
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        int size= tables.size();

        return ExitFlowResDto.builder().exitCount(size).build();
    }

    @Override
    public BounceResDto getBounceCounts(BounceReqDto bounceReqDto,int memberId) {
        memberServiceClient.validationMemberAndApplication(MemberValidReqDto.create(bounceReqDto.getApplicationToken(),memberId));

        Restrictions restrictions = Restrictions.and(
            Restrictions.measurement().equal("data"),
            Restrictions.tag("applicationToken").equal("\""+bounceReqDto.getApplicationToken()+"\""),
            Restrictions.tag("currentUrl").equal("\""+bounceReqDto.getCurrentUrl()+"\"")
        );
        Flux query = Flux.from(bucket)
            .range(0L)
            .filter(restrictions)
            .groupBy("activityId")
            .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
            .yield();
        System.out.println(query.toString());
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        int count=0;
        for(FluxTable table: tables){
            List<FluxRecord> records = table.getRecords();
            if(records.size()==1)
                ++count;
        }

        return BounceResDto.builder().count(count).build();
    }
}
