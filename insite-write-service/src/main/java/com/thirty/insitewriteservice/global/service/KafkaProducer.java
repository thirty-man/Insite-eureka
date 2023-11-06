package com.thirty.insitewriteservice.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.thirty.insitewriteservice.write.dto.DataReqDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaProducer {
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public String send(String topic, DataReqDto dataReqDto) {
		String message = convertToInfluxLineProtocol(dataReqDto);
		try {
			kafkaTemplate.send(topic, message);
			log.info("Kafka Producer send data from Member microservice: {}", message);
		} catch (Exception e) {
			log.error("Failed to send message to Kafka. Topic: {}, Message: {}, Error: {}", topic, message,
				e.getMessage());
		}
		return message;
	}

	public String convertToInfluxLineProtocol(DataReqDto dataReqDto) {
		StringBuilder sb = new StringBuilder();
		//요청 시 data 구분
		// Measurement 추가
		sb.append("data");

		// Tags 추가
		sb.append(",cookieId=").append(dataReqDto.getCookieId());
		sb.append(",currentUrl=").append(dataReqDto.getCurrentUrl());
		sb.append(",activityId=").append(dataReqDto.getActivityId());
		sb.append(",applicationToken=").append(dataReqDto.getApplicationToken());
		sb.append(",responseTime=").append(dataReqDto.getResponseTime());
		sb.append(",deviceId=").append(dataReqDto.getDeviceId());
		sb.append(",osId=").append(dataReqDto.getOsId());
		sb.append(",isNew=").append(dataReqDto.isNew());
		sb.append(",beforeUrl=").append(dataReqDto.getBeforeUrl());

		// 태그와 필드 사이에 공백 추가
		sb.append(" ");

		// 필드 추가 (단일 필드인 경우)
		sb.append("applicationUrl=\"").append(dataReqDto.getApplicationUrl()).append("\"");

		// Timestamp 추가 (나노초 단위로 변환)
		sb.append(" ").append(System.currentTimeMillis() * 1000000);

		return sb.toString();
	}

}