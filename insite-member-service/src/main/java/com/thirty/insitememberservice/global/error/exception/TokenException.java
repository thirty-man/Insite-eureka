package com.thirty.insitememberservice.global.error.exception;


import com.thirty.insitememberservice.global.error.ErrorCode;

public class TokenException extends BusinessException {

	public TokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
