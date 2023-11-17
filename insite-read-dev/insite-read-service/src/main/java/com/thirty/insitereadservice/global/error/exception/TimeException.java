package com.thirty.insitereadservice.global.error.exception;

import com.thirty.insitereadservice.global.error.ErrorCode;

public class TimeException extends BusinessException{
    public TimeException(ErrorCode errorCode) {
        super(errorCode);
    }

}
