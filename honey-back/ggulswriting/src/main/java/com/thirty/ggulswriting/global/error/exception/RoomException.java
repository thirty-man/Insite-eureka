package com.thirty.ggulswriting.global.error.exception;

import com.thirty.ggulswriting.global.error.ErrorCode;

public class RoomException extends BusinessException {

	public RoomException(ErrorCode errorCode) {
		super(errorCode);
	}
}
