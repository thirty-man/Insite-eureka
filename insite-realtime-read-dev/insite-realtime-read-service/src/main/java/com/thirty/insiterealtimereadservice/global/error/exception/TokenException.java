package com.thirty.insiterealtimereadservice.global.error.exception;


import com.thirty.insiterealtimereadservice.global.error.ErrorCode;

public class TokenException extends BusinessException {

	public TokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
