package com.thirty.insitememberservice.global.error.exception;

import com.thirty.insitememberservice.global.error.ErrorCode;

public class ApplicationException extends BusinessException{

    public ApplicationException(ErrorCode errorCode){ super(errorCode);}
}
