package com.thirty.ggulswriting.message.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageSendReqDto {
	@NotNull
	private int memberIdTo;

	@NotNull
	private int roomId;

	@NotNull
	private String nickName;

	@Size(max = 450, message = "내용은 450자 제한입니다.")
	private String content;

	@NotNull
	private String honeyCaseType;

}
