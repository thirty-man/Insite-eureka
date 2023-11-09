package com.thirty.insitereadservice.global.error.exception;

import com.thirty.insitereadservice.global.error.ErrorCode;

public class ButtonException extends BusinessException{
    public ButtonException(ErrorCode errorCode) {
        super(errorCode);
    }

}
