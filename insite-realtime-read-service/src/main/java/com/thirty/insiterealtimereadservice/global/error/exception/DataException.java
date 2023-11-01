package com.thirty.insiterealtimereadservice.global.error.exception;


import com.thirty.insiterealtimereadservice.global.error.ErrorCode;

public class DataException extends BusinessException {

	public DataException(ErrorCode errorCode) {
		super(errorCode);
	}
}
