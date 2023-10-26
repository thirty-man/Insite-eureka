package com.thirty.insite.member.dto.request;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginReqDto {

	@NotEmpty
	private String code;

}