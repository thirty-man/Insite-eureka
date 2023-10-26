package com.thirty.insite.global.error.exception;

import com.thirty.insite.global.error.ErrorCode;

public class TokenException extends BusinessException {

	public TokenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
