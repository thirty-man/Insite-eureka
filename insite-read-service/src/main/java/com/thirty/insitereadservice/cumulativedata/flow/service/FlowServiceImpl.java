package com.thirty.insitereadservice.cumulativedata.flow.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.cumulativedata.flow.dto.reqDto.UrlFlowReqDto;
import com.thirty.insitereadservice.cumulativedata.flow.dto.resDto.UrlFlowResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowServiceImpl implements FlowService {
    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.bucket}")
    private String bucket;

    private char[] token = "A8b0xLYTE1K8Xfmen0Fei7HzzWwTzazTHtKFJMR8zOn5iiA5H1xz4Ln-V42ClEg4c2XAlFdjsSZ7w-5JAV8S9Q==".toCharArray();

    @Resource
    private InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org);


    @Override
    public UrlFlowResDto getUrlFlow(UrlFlowReqDto urlFlowReqDto) {
        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal("\""+urlFlowReqDto.getApplicationToken()+"\""),
                Restrictions.tag("currentUrl").equal("\""+urlFlowReqDto.getCurrentUrl()+"\"")
        );
        Flux query = Flux.from(bucket)
                .range(-30L, ChronoUnit.DAYS)
                .filter(restrictions)
                .groupBy("beforeUrl")
                .pivot(new String[]{"_time"},new String[]{"_field"},"_value")
                .yield();
        System.out.println(query.toString());
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());

        return null;
    }
}
