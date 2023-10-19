package com.thirty.ggulswriting.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	//회원
	ALREADY_WITHDRAWAL_MEMBER(HttpStatus.UNAUTHORIZED, "001", "탈퇴한 회원입니다."),
	NOT_EXIST_MEMBER(HttpStatus.UNAUTHORIZED, "002", "존재하지 않는 회원입니다."),
	;

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private HttpStatus httpStatus;
	private String errorCode;
	private String message;
}
