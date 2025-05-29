package com.unity.tribe.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class TribeAuthenticateException extends AuthenticationException {

    private final ErrorCode errorCode;

    public TribeAuthenticateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public TribeAuthenticateException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return errorCode.getHttpStatus();
    }
}
