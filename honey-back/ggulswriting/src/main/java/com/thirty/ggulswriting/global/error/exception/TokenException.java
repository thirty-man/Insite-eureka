package com.thirty.ggulswriting.global.error.exception;

import com.thirty.ggulswriting.global.error.ErrorCode;

public class TokenException extends BusinessException{

    public TokenException(ErrorCode errorCode){super(errorCode);}
}
