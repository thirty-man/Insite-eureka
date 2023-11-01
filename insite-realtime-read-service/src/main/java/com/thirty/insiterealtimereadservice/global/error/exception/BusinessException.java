package com.thirty.insiterealtimereadservice.global.error.exception;

import com.thirty.insiterealtimereadservice.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
