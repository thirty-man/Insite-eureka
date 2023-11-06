package com.thirty.insitereadservice.global.error.exception;

import com.thirty.insitereadservice.global.error.ErrorCode;

public class TokenException extends BusinessException{
    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }

}
