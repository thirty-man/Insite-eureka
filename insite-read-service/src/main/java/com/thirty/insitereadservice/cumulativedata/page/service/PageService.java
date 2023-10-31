package com.thirty.insitereadservice.cumulativedata.page.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PageService {

    @Resource
    private InfluxDBClient influxDBClient;

    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.bucket}")
    private String bucket;

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

}
