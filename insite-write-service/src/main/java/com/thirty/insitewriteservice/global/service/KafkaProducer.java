package com.thirty.insitewriteservice.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.thirty.insitewriteservice.write.dto.ButtonReqDto;
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

	public String sendData(String topic, DataReqDto dataReqDto) {
		String message = convertToInfluxLineProtocolData(dataReqDto);
		try {
			kafkaTemplate.send(topic, message);
			log.info("Kafka Producer send data from Member microservice: {}", message);
		} catch (Exception e) {
			log.error("Failed to send message to Kafka. Topic: {}, Message: {}, Error: {}", topic, message,
				e.getMessage());
		}
		return message;
	}

	public String sendButton(String topic, ButtonReqDto buttonReqDto) {
		String message = convertToInfluxLineProtocolButton(buttonReqDto);
		try {
			kafkaTemplate.send(topic, message);
			log.info("Kafka Producer send data from Member microservice: {}", message);
		} catch (Exception e) {
			log.error("Failed to send message to Kafka. Topic: {}, Message: {}, Error: {}", topic, message,
				e.getMessage());
		}
		return message;
	}

	public String convertToInfluxLineProtocolData(DataReqDto dataReqDto) {
		StringBuilder sb = new StringBuilder();
		// 요청 시 data 구분
		// Measurement 추가
		sb.append("data");

		// Tags 추가
		sb.append(",cookieId=").append(escapeField(dataReqDto.getCookieId()));
		sb.append(",currentUrl=").append(escapeField(dataReqDto.getCurrentUrl()));
		sb.append(",beforeUrl=").append(escapeField(dataReqDto.getBeforeUrl()));
		sb.append(",referrer=").append(escapeField(dataReqDto.getReferrer()));
		sb.append(",language=").append(escapeField(dataReqDto.getLanguage()));
		sb.append(",responseTime=").append(escapeField(dataReqDto.getResponseTime()));
		sb.append(",osId=").append(escapeField(dataReqDto.getOsId()));
		sb.append(",isNew=").append(dataReqDto.isNew() ? "true" : "false");
		sb.append(",applicationToken=").append(escapeField(dataReqDto.getApplicationToken()));
		sb.append(",activityId=").append(escapeField(dataReqDto.getActivityId()));
		sb.append(",requestCnt=").append(escapeField(dataReqDto.getRequestCnt()));

		// 태그와 필드 사이에 공백 추가
		sb.append(" ");

		// 필드 추가 (단일 필드인 경우)
		sb.append("applicationUrl=\"").append(escapeField(dataReqDto.getApplicationUrl())).append("\"");

		// Timestamp 추가 (나노초 단위로 변환)
		sb.append(" ").append(System.currentTimeMillis() * 1000000);

		return sb.toString();
	}

	private String escapeField(String field) {
		return field.replace(" ", "\\ ").replace(",", "\\,").replace("=", "\\=");
	}

	public String convertToInfluxLineProtocolButton(ButtonReqDto buttonReqDto) {
		StringBuilder sb = new StringBuilder();
		//요청 시 data 구분
		// Measurement 추가
		sb.append("button");

		// Tags 추가
		sb.append(",cookieId=").append(buttonReqDto.getCookieId());
		sb.append(",currentUrl=").append(buttonReqDto.getCurrentUrl());
		sb.append(",activityId=").append(buttonReqDto.getActivityId());
		sb.append(",applicationToken=").append(buttonReqDto.getApplicationToken());
		sb.append(",name=").append(buttonReqDto.getName());
		sb.append(",requestCnt=").append(buttonReqDto.getRequestCnt());

		// 태그와 필드 사이에 공백 추가
		sb.append(" ");

		// 필드 추가 (단일 필드인 경우)
		sb.append("applicationUrl=\"").append(buttonReqDto.getApplicationUrl()).append("\"");

		// Timestamp 추가 (나노초 단위로 변환)
		sb.append(" ").append(System.currentTimeMillis() * 1000000);

		return sb.toString();
	}

}