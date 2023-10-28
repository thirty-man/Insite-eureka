package com.thirty.insitememberservice.member.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class LoginReqDto {

	@NotEmpty
	private String code;

}