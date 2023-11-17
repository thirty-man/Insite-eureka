package com.thirty.insitereadservice.global.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

@Service
public class InfluxDBService {

	@Resource
	private InfluxDBClient influxDBClient;

	@Value("${influxdb.org}")
	private String org;

	@Value("${influxdb.bucket}")
	private String bucket;

	public void queryData() {
		String flux = "from(bucket: \"insite\")"
			+ " |> range(start: -1h)"
			+ " |> filter(fn: (r) => r._measurement == \"data\" and r.serviceToken == \"1234\")";
		List<FluxTable> tables = influxDBClient.getQueryApi().query(flux);

		for (FluxTable fluxTable : tables) {
			List<FluxRecord> records = fluxTable.getRecords();
			for (FluxRecord record : records) {
				// 여기서 각 레코드의 필드와 태그 값을 가져올 수 있습니다.
				String cookieId = record.getValueByKey("cookieId").toString();
				String currentUrl = record.getValueByKey("currentUrl").toString();
				// 나머지 필드와 태그도 동일한 방식으로 처리 가능
			}
		}
	}

	// public void writeDataToData(DataReqDto dataReqDto) {
	//
	// 	try (WriteApi writeApi = influxDBClient.getWriteApi()) {
	// 		Point point = Point.measurement("data")
	// 			.addTag("cookieId", dataReqDto.getCookieId())
	// 			.addTag("currentUrl", dataReqDto.getCurrentUrl())
	// 			.addTag("activityId", dataReqDto.getActivityId())
	// 			.addTag("serviceToken", dataReqDto.getServiceToken())
	// 			.addField("beforeUrl", dataReqDto.getBeforeUrl())
	// 			.addField("responseTime", dataReqDto.getResponseTime())
	// 			.addField("deviceId", dataReqDto.getDeviceId())
	// 			.addField("osId", dataReqDto.getOsId())
	// 			.addField("isNew", dataReqDto.isNew())
	// 			.time(Instant.now(), WritePrecision.MS);
	//
	// 		writeApi.writePoint(bucket, org, point);
	// 	}
	// }

}
