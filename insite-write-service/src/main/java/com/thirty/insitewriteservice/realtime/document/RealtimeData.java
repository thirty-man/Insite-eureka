//package com.thirty.insitewriteservice.realtime.document;
//
//import java.time.LocalDateTime;
//
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@NoArgsConstructor
//@Document(collection = "realtimeData")
//@Builder
//@AllArgsConstructor
//public class RealtimeData {
//
//	@Id
//	private String dataId;
//
//	private String cookieId;
//
//	private String currentUrl;
//
//	private String beforeUrl;
//
//	private String responseTime;
//
//	private String deviceId;
//
//	private String osId;
//
//	@CreatedDate
//	LocalDateTime createTime;
//
//	private boolean isNew;
//
//	private String serviceToken;
//
//	private String activityId;
//
//}
