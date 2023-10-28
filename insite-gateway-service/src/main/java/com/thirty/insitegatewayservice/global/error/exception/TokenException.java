package com.thirty.insitegatewayservice.global.error.exception;


import com.thirty.insitegatewayservice.global.error.ErrorCode;

public class TokenException extends BusinessException {

	public TokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
