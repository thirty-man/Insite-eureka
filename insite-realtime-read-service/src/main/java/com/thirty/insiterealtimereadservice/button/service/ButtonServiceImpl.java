package com.thirty.insiterealtimereadservice.button.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.Query;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.thirty.insiterealtimereadservice.button.dto.response.RealTimeCountResDto;
import com.thirty.insiterealtimereadservice.button.measurement.Button;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ButtonServiceImpl implements ButtonService{

    @Resource
    private InfluxDBClient influxDBClient;


    @Override
    public RealTimeCountResDto readRealTimeCount(String serviceToken, String name) {
        QueryApi queryApi = influxDBClient.getQueryApi();
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder
            .append("from(bucket: \"insite\")")
            .append(" |> range(start: -30m)")  // 현재로부터 30분 이전
            .append(" |> filter(fn: (r) => r._measurement == \"button\" and r.serviceToken == \"")
            .append(serviceToken)
            .append("\" and r.name == \"")
            .append(name)
            .append("\")")
            .append(" |> count()");

        List<FluxTable> tables = queryApi.query(queryBuilder.toString());
        int count = 0;
        for (FluxTable fluxTable : tables) {
            List<FluxRecord> records = fluxTable.getRecords();
            if (records.size() > 0) {
                FluxRecord record = records.get(0);
                String countStringValue = record.getValueByKey("_value").toString();
                count = Integer.valueOf(countStringValue);
            }
        }
        return RealTimeCountResDto.create(count);
    }





    private List<Button> readAll(){
        QueryApi queryApi = influxDBClient.getQueryApi();
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder
            .append("from(bucket: \"insite\")")
            .append(" |> range(start:0)")
            .append(" |> filter(fn: (r) => r._measurement == \"button\")");

        List<FluxTable> tables = queryApi.query(queryBuilder.toString());
        List<Button> buttonList = new ArrayList<>();

        for (FluxTable fluxTable : tables) {
			List<FluxRecord> records = fluxTable.getRecords();
			for (FluxRecord record : records) {
				String cookieId = record.getValueByKey("cookieId").toString();
				String currentUrl = record.getValueByKey("_value").toString();
				String name = record.getValueByKey("name").toString();
                String serviceToken = record.getValueByKey("serviceToken").toString();

                //버튼 리스트에 추가
               buttonList.add(Button.create(name, currentUrl, cookieId, serviceToken));
			}
		}
        return buttonList;
    }
}
