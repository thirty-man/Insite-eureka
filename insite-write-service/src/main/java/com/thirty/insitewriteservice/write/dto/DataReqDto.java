package com.thirty.insitewriteservice.write.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataReqDto {
	@NotNull
	private String cookieId;
	@NotNull
	private String currentUrl;
	@NotNull
	private String beforeUrl;
	@NotNull
	private String referrer;
	@NotNull
	private String language;
	@NotNull
	private String responseTime;
	@NotNull
	private String osId;
	@NotNull
	private boolean isNew;
	@NotNull
	private String applicationToken;
	@NotNull
	private String applicationUrl;

	private String activityId;

	private String requestCnt;

	public void updateActivityId(String newActivityId){
		this.activityId = newActivityId;
	}
	public void updateRequestCnt(String newRequestCnt){
		this.requestCnt = newRequestCnt;
	}
}
