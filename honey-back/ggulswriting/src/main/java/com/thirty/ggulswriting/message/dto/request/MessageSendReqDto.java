package com.thirty.ggulswriting.room.dto.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageSendReqDto {
	@NotNull
	private int memberIdFrom;

	@NotNull
	private int memberIdTo;

	@NotNull
	private int roomId;

	@NotNull
	private String nickName;

	@Size(max = 100, message = "내용은 100자 제한입니다.")
	private String content;

	@NotNull
	private String honeyCaseType;

}
