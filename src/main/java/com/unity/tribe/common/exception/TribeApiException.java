package com.unity.tribe.common.exception;

import org.springframework.http.HttpStatus;

public class TribeApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public TribeApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


    public HttpStatus getStatus() {
        return errorCode.getHttpStatus();
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
