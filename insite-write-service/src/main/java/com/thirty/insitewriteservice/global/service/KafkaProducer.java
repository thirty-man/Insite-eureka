package com.thirty.insitewriteservice.global.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.thirty.insitewriteservice.write.dto.ButtonReqDto;
import com.thirty.insitewriteservice.write.dto.DataReqDto;
import lombok.extern.slf4j.Slf4j;
import java.time.ZoneOffset;

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
		// Measurement 추가
		sb.append("data");
		// Tags 추가
		sb.append(",cookieId=").append(dataReqDto.getCookieId());
		sb.append(",currentUrl=").append(dataReqDto.getCurrentUrl());
		sb.append(",beforeUrl=").append(dataReqDto.getBeforeUrl() == null || dataReqDto.getBeforeUrl().length() <= 1 ? "null" : dataReqDto.getBeforeUrl());
		sb.append(",referrer=").append(dataReqDto.getReferrer() == null || dataReqDto.getReferrer().length() <= 1 ? "null" : dataReqDto.getReferrer());
		sb.append(",language=").append(dataReqDto.getLanguage());
		sb.append(",responseTime=").append(dataReqDto.getResponseTime());
		sb.append(",osId=").append(dataReqDto.getOsId());
		sb.append(",isNew=").append(dataReqDto.isNew() ? "true" : "false");
		sb.append(",applicationToken=").append(dataReqDto.getApplicationToken());
		sb.append(",activityId=").append(dataReqDto.getActivityId());
		sb.append(",requestCnt=").append(dataReqDto.getRequestCnt());
		// 태그와 필드 사이에 공백 추가
		sb.append(" ");
		// 필드 추가 (단일 필드인 경우)
		sb.append("applicationUrl=\"").append(dataReqDto.getApplicationUrl()).append("\"");
		// Timestamp 추가 (나노초 단위로 변환) (UTC 변환 추가)
		sb.append(" ").append((System.currentTimeMillis() + ZoneOffset.ofHours(9).getTotalSeconds() * 1000L) * 1000000);
		return sb.toString();
	}


	public String convertToInfluxLineProtocolButton(ButtonReqDto buttonReqDto) {
		StringBuilder sb = new StringBuilder();
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
		sb.append(" ").append(System.currentTimeMillis() * 1000000 + ZoneOffset.ofHours(9).getTotalSeconds() * 1000L);
		return sb.toString();
	}
}