package com.thirty.insitegatewayservice.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
	//회원
	ALREADY_WITHDRAWAL_MEMBER(HttpStatus.UNAUTHORIZED, "001", "탈퇴한 회원입니다."),
	NOT_EXIST_MEMBER(HttpStatus.UNAUTHORIZED, "002", "존재하지 않는 회원입니다."),
	//room
	NOT_EXIST_ROOM(HttpStatus.BAD_REQUEST, "001", "존재하지 않는 방입니다."),
	//참가자
	ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "001", "이미 방에 참가중인 회원입니다."),
	//토큰
	EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "000", "만료된 토큰입니다."),
	NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "002", "유효하지 않은 토큰입니다."),

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
