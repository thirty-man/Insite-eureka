package com.thirty.insitememberservice.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
	//회원
	ALREADY_WITHDRAWAL_MEMBER(HttpStatus.UNAUTHORIZED, "001", "탈퇴한 회원입니다."),
	NOT_EXIST_MEMBER(HttpStatus.UNAUTHORIZED, "002", "존재하지 않는 회원입니다."),
	NOT_OWNER_MEMBER(HttpStatus.UNAUTHORIZED,"003","해당 어플리케이션에 권한이 없습니다."),
	//room
	NOT_EXIST_ROOM(HttpStatus.BAD_REQUEST, "001", "존재하지 않는 방입니다."),
	//참가자
	ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "001", "이미 방에 참가중인 회원입니다."),
	//토큰
	EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "001", "만료된 토큰입니다."),
	NOT_VALID_TOKEN(HttpStatus.UNAUTHORIZED, "002", "유효하지 않은 토큰입니다."),
	//어플리케이션
	NOT_EXIST_APPLICATION(HttpStatus.BAD_REQUEST,"001","존재하지 않는 어플리케이션입니다."),
	ALREADY_EXIST_APPLICATION(HttpStatus.BAD_REQUEST,"002","이미 존재하는 어플리케이션입니다."),
	//버튼
	ALREADY_EXIST_BUTTON_NAME(HttpStatus.BAD_REQUEST,"001","이미 존재하는 버튼이름입니다."),
	NOT_EXIST_BUTTON(HttpStatus.BAD_REQUEST,"002","존재하지 않는 버튼입니다.")
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
