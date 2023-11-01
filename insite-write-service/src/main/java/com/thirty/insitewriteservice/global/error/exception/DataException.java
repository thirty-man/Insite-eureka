package com.thirty.insitewriteservice.global.error.exception;

import com.thirty.insitewriteservice.global.error.ErrorCode;

public class DataException extends BusinessException {

	public DataException(ErrorCode errorCode) {
		super(errorCode);
	}
}
