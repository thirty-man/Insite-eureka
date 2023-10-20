package com.thirty.ggulswriting.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	//회원
	ALREADY_WITHDRAWAL_MEMBER(HttpStatus.UNAUTHORIZED, "001", "탈퇴한 회원입니다."),
	NOT_EXIST_MEMBER(HttpStatus.UNAUTHORIZED, "002", "존재하지 않는 회원입니다."),
	//room
	NOT_EXIST_ROOM(HttpStatus.BAD_REQUEST, "001", "존재하지 않는 방입니다."),
	DELETED_ROOM(HttpStatus.BAD_REQUEST, "002", "삭제된 방입니다."),
	//참가자
	ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "001", "이미 방에 참가중인 회원입니다."),
	NOT_EXIST_PARTICIPATION(HttpStatus.BAD_REQUEST, "002", "방에 참여하지 않은 회원입니다."),
	ALREADY_OUT_MEMBER(HttpStatus.BAD_REQUEST, "003", "이미 방에서 나간 회원입니다.");

	ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}

	private HttpStatus httpStatus;
	private String errorCode;
	private String message;
}
