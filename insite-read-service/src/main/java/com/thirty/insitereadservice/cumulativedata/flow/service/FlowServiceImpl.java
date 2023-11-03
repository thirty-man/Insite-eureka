package com.thirty.insitereadservice.cumulativedata.flow.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.cumulativedata.flow.dto.ReferrerFlowDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.UrlFlowDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.BounceReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.ExitFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.ReferrerFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.UrlFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.BounceResDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.ExitFlowResDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.ReferrerFlowResDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.UrlFlowResDto;
import com.thirty.insitereadservice.feignclient.MemberServiceClient;
import com.thirty.insitereadservice.feignclient.dto.request.MemberValidReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowServiceImpl implements FlowService {
    private final MemberServiceClient memberServiceClient;

    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.bucket}")
    private String bucket;

    private char[] token = "A8b0xLYTE1K8Xfmen0Fei7HzzWwTzazTHtKFJMR8zOn5iiA5H1xz4Ln-V42ClEg4c2XAlFdjsSZ7w-5JAV8S9Q==".toCharArray();

    @Resource
    private final InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org);


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
        System.out.println(query.toString());
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
    public ExitFlowResDto getExitFlow(ExitFlowReqDto exitFlowReqDto,int memberId) {
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
