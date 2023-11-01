package com.thirty.insitereadservice.cumulativedata.page.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.InfluxQLQueryResult;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import com.thirty.insitereadservice.cumulativedata.page.dto.DataDto;
import com.thirty.insitereadservice.cumulativedata.page.dto.reqDto.PageViewReqDto;
import com.thirty.insitereadservice.cumulativedata.page.dto.resDto.PageViewResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {


    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.bucket}")
    private String bucket;

    private char[] token = "A8b0xLYTE1K8Xfmen0Fei7HzzWwTzazTHtKFJMR8zOn5iiA5H1xz4Ln-V42ClEg4c2XAlFdjsSZ7w-5JAV8S9Q==".toCharArray();

    @Resource
    private InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org);

//    public void queryData() {
//        String flux = "from(bucket: \"insite\")"
//                + " |> range(start: -1h)"
//                + " |> filter(fn: (r) => r._measurement == \"data\" and r.serviceToken == \"1234\")";
//        List<FluxTable> tables = influxDBClient.getQueryApi().query(flux);
//
//        for (FluxTable fluxTable : tables) {
//            List<FluxRecord> records = fluxTable.getRecords();
//            for (FluxRecord record : records) {
//                // 여기서 각 레코드의 필드와 태그 값을 가져올 수 있습니다.
//                String cookieId = record.getValueByKey("cookieId").toString();
//                String currentUrl = record.getValueByKey("currentUrl").toString();
//                // 나머지 필드와 태그도 동일한 방식으로 처리 가능
//            }
//        }
//    }


    @Override
    public PageViewResDto getPageView(PageViewReqDto pageViewReqDto) {


        Restrictions restrictions = Restrictions.and(
                Restrictions.measurement().equal("data"),
                Restrictions.tag("applicationToken").equal(pageViewReqDto.getApplicationToken()),
                Restrictions.tag("currentUrl").equal(pageViewReqDto.getCurrentUrl())
        );
        Flux query = Flux.from(bucket)
                .range(-30L, ChronoUnit.DAYS)
                .filter(restrictions)
                .groupBy("_time")
                .yield();

        DataDto dataDto = new DataDto();
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<FluxTable> tables = queryApi.query(query.toString());
        System.out.println(tables.size());
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            System.out.println(records.size());
            for (FluxRecord fluxRecord : records) {
                System.out.println(fluxRecord.getValueByKey("_value"));

            }
        }
//        String flux = "from(bucket: \"insite\")"
//                + " |> range(start:-12h)"
//                + " |>filter(fn: (r) =>r._measurement == \"data\" and r.applicationToken == \"token1\")";
//        List<FluxTable> tables = influxDBClient.getQueryApi().query(flux);
//        int pageView=0;
//        for(FluxTable fluxTable:tables){
//            List<FluxRecord> records = fluxTable.getRecords();
//            for(FluxRecord record:records){
//                System.out.println(record.getValueByKey("applicationToken").toString());
//                ++pageView;
//
//            }
//        }
            PageViewResDto pageViewResDto = PageViewResDto.builder().pageView(0).build();

            return pageViewResDto;
        }

}