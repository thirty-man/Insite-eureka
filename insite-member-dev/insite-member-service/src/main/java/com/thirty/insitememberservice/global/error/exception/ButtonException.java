package com.thirty.insitememberservice.global.error.exception;

import com.thirty.insitememberservice.global.error.ErrorCode;

public class ButtonException extends RuntimeException {
    private ErrorCode errorCode;

    public ButtonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
