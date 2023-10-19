package com.thirty.ggulswriting.global.error.exception;

import com.thirty.ggulswriting.global.error.ErrorCode;

public class MemberException extends BusinessException {

	public MemberException(ErrorCode errorCode) {
		super(errorCode);
	}
}
