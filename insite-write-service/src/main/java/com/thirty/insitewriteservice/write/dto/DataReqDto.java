package com.thirty.insitewriteservice.write.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.thirty.insitewriteservice.realtime.document.RealtimeData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataReqDto {
	@NotNull
	private String cookieId;
	@NotNull
	private String currentUrl;
	@NotNull
	private String beforeUrl;
	@NotNull
	private String responseTime;
	@NotNull
	private String deviceId;
	@NotNull
	private String osId;
	@NotNull
	private boolean isNew;
	@NotNull
	private String serviceToken;
	@NotNull
	private String activityId;
	//마이에스큐엘 검증과정도 추가하기
	//쿠키 아이디중 가장 마지막 접속 가져와서 30분 지나면 새로운 액이티비 부여
	// 아니면 원래 기존값 넣기

	public RealtimeData toRealtimeDataOf() {
		return RealtimeData.builder()
			.cookieId(this.cookieId)
			.currentUrl(this.currentUrl)
			.beforeUrl(this.beforeUrl)
			.responseTime(this.responseTime)
			.deviceId(this.deviceId)
			.osId(this.osId)
			.isNew(this.isNew)
			.serviceToken(this.serviceToken)
			.activityId(this.activityId)
			.build();
	}
}
