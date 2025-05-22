package com.unity.tribe.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TribeApiException.class)
    public ResponseEntity<ErrorResponse> handleException(TribeApiException e) {

        log.error("[TribeApiException] code={}, message={}",
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e);

        return ResponseEntity
                .status(e.getStatus().value())
                .body(ErrorResponse.builder()
                        .code(e.getErrorCode().getCode())
                        .message(e.getErrorCode().getMessage())
                        .build());
    }
}
