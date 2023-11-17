package com.thirty.insitememberservice.global.error.exception;


import com.thirty.insitememberservice.global.error.ErrorCode;

public class MemberException extends BusinessException {

	public MemberException(ErrorCode errorCode) {
		super(errorCode);
	}
}
