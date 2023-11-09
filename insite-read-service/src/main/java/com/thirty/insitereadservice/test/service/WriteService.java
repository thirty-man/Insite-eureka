package com.thirty.insitereadservice.test.service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.thirty.insitereadservice.test.dto.AbnormalReqDto;
import com.thirty.insitereadservice.test.dto.ButtonReqDto;
import com.thirty.insitereadservice.test.dto.DataReqDto;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WriteService {

    @Resource
    private InfluxDBClient influxDBClient;

    @Value("${influxdb.org}")
    private String org;

    @Value("${influxdb.bucket}")
    private String bucket;

//	public void queryData() {
//		String flux = "from(bucket: \"insite\")"
//			+ " |> range(start: -1h)"
//			+ " |> filter(fn: (r) => r._measurement == \"data\" and r.serviceToken == \"1234\")";
//		List<FluxTable> tables = influxDBClient.getQueryApi().query(flux);
//
//		for (FluxTable fluxTable : tables) {
//			List<FluxRecord> records = fluxTable.getRecords();
//			for (FluxRecord record : records) {
//				// 여기서 각 레코드의 필드와 태그 값을 가져올 수 있습니다.
//				String cookieId = record.getValueByKey("cookieId").toString();
//				String currentUrl = record.getValueByKey("currentUrl").toString();
//				// 나머지 필드와 태그도 동일한 방식으로 처리 가능
//			}
//		}
//	}

    public void writeDataToData(DataReqDto dataReqDto) {

        try (WriteApi writeApi = influxDBClient.getWriteApi()) {
            Point point = Point.measurement("data")
                .addTag("cookieId", dataReqDto.getCookieId())
                .addTag("currentUrl", dataReqDto.getCurrentUrl())
                .addTag("beforeUrl", dataReqDto.getBeforeUrl())
                .addTag("referrer", dataReqDto.getReferrer())
                .addTag("language",dataReqDto.getLanguage())
                .addTag("responseTime",dataReqDto.getResponseTime())
                .addTag("osId", dataReqDto.getOsId())
                .addTag("isNew", dataReqDto.getIsNew())
                .addTag("applicationToken", dataReqDto.getApplicationToken())
                .addTag("activityId", dataReqDto.getActivityId())
                .addTag("requestCnt", dataReqDto.getRequestCnt())
                .addField("applicationUrl", dataReqDto.getApplicationUrl())
                .time(Instant.now(), WritePrecision.MS);
            System.out.println("data={}"+ dataReqDto);
            writeApi.writePoint(bucket, org, point);
        }
    }

    public void writeDataToButton(ButtonReqDto buttonReqDto) {

        try (WriteApi writeApi = influxDBClient.getWriteApi()) {
            Point point = Point.measurement("button")
                .addTag("name", buttonReqDto.getName())
                .addTag("currentUrl", buttonReqDto.getCurrentUrl())
                .addTag("cookieId", buttonReqDto.getCookieId())
                .addTag("activityId", buttonReqDto.getActivityId())
                .addTag("applicationToken", buttonReqDto.getApplicationToken())
                .addTag("requestCnt", buttonReqDto.getRequestCnt())
                .addField("applicationUrl", buttonReqDto.getApplicationUrl())
//                .time(Instant.now().minus(1, ChronoUnit.DAYS), WritePrecision.MS);
                .time(Instant.now(), WritePrecision.MS);
            System.out.println("button= "+buttonReqDto);
            writeApi.writePoint(bucket, org, point);
        }
    }

    public void writeDataToAbnormal(AbnormalReqDto abnormalReqDto) {

        try (WriteApi writeApi = influxDBClient.getWriteApi()) {
            Point point = Point.measurement("abnormal")
                .addTag("isRead", abnormalReqDto.getIsRead())
                .addTag("cookieId", abnormalReqDto.getCookieId())
                .addTag("applicationToken", abnormalReqDto.getApplicationToken())
                .addTag("currentUrl", abnormalReqDto.getCurrentUrl())
                .addField("applicationUrl",abnormalReqDto.getApplicationUrl())
//                .time(Instant.now().minus(1, ChronoUnit.DAYS), WritePrecision.MS);
                .time(Instant.now(), WritePrecision.MS);
            System.out.println("abnormal= "+abnormalReqDto);
            writeApi.writePoint(bucket, org, point);
        }
    }
}