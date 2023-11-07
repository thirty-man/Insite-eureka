package com.thirty.insitewriteservice.global.service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import com.influxdb.client.QueryApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;

import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.thirty.insitewriteservice.write.dto.DataReqDto;

@Service
public class InfluxDBService {

	@Resource
	private InfluxDBClient influxDBClient;

	@Value("${influxdb.org}")
	private String org;

	@Value("${influxdb.bucket}")
	private String bucket;

	public void writeDataToData(DataReqDto dataReqDto) {

		try (WriteApi writeApi = influxDBClient.getWriteApi()) {
			Point point = Point.measurement("data")
				.addTag("cookieId", dataReqDto.getCookieId())
				.addTag("currentUrl", dataReqDto.getCurrentUrl())
				.addTag("beforeUrl", dataReqDto.getBeforeUrl())
				.addTag("referrer", dataReqDto.getReferrer())
				.addTag("language", dataReqDto.getLanguage())
				.addTag("responseTime", dataReqDto.getResponseTime())
				.addTag("osId", dataReqDto.getOsId())
				.addTag("isNew", Boolean.toString(dataReqDto.isNew()))
				.addTag("applicationToken", dataReqDto.getApplicationToken())
				.addField("applicationUrl", dataReqDto.getApplicationUrl())
				.addTag("activityId", dataReqDto.getActivityId())
				.addTag("requestCnt", dataReqDto.getRequestCnt())
				.time(Instant.now(), WritePrecision.NS);

			writeApi.writePoint(bucket, org, point);
		}
	}
	
	public void writeDataToAbnormal() {
		try (WriteApi writeApi = influxDBClient.getWriteApi()) {
			Point point = Point.measurement("abnormal")
				.addField("value", 456)
				.time(Instant.now(), WritePrecision.MS);

			writeApi.writePoint(bucket, org, point);
		}
	}

	public void writeDataToButton() {
		try (WriteApi writeApi = influxDBClient.getWriteApi()) {
			Point point = Point.measurement("button")
				.addField("value", 456)
				.time(Instant.now(), WritePrecision.MS);

			writeApi.writePoint(bucket, org, point);
		}
	}

	//비동기
	// public void addTSData() {
	// 	Point row = Point.measurement("launcher_client_connection")
	// 		.addTag("privateIp", vo.getPrivateIp())
	// 		.addTag("port", vo.getPort())
	// 		.addField("clientCnt", vo.getClientCnt());
	// 	influxDBClient.getWriteApiBlocking().writePoint(row);
	// }

	//대량넣기
	// try (WriteApi writeApi = influxDBClient.getWriteApi()) {
	// 	List<Point> points = new ArrayList<>();
	//
	// 	// 데이터 포인트 생성 및 리스트에 추가
	// 	Point point1 = Point.measurement("measurement1").addField("field1", value1);
	// 	Point point2 = Point.measurement("measurement2").addField("field2", value2);
	// 	points.add(point1);
	// 	points.add(point2);
	//
	// 	// 리스트에 있는 모든 데이터 포인트를 InfluxDB에 한 번에 쓰기
	// 	writeApi.writePoints(bucket, org, points);
	// }

}
