package com.thirty.insitereadservice.global.error.exception;

import com.thirty.insitereadservice.global.error.ErrorCode;

public class DataException extends BusinessException {

	public DataException(ErrorCode errorCode) {
		super(errorCode);
	}
}
