package com.thirty.ggulswriting.global.error.exception;

import com.thirty.ggulswriting.global.error.ErrorCode;

public class ParticipationException extends BusinessException {

	public ParticipationException(ErrorCode errorCode) {
		super(errorCode);
	}
}
