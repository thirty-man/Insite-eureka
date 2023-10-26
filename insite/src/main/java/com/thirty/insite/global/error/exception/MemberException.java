package com.thirty.insite.global.error.exception;

import com.thirty.insite.global.error.ErrorCode;

public class MemberException extends BusinessException {

	public MemberException(ErrorCode errorCode) {
		super(errorCode);
	}
}
