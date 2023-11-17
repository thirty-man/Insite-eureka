package com.thirty.ggulswriting.global.error.exception;

import com.thirty.ggulswriting.global.error.ErrorCode;

public class MessageException extends BusinessException {

	public MessageException(ErrorCode errorCode) {
		super(errorCode);
	}
}
