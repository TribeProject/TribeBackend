package com.unity.tribe.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TribeApiException.class)
    public ResponseEntity<ErrorResponse> handleTribeApiException(TribeApiException e) {
        log.error("[TribeApiException] code={}, message={}",
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage(),
                e);

        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponse.builder()
                        .code(e.getErrorCode().getCode())
                        .message(e.getErrorCode().getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("[Exception] message={}", e.getMessage(), e);

        return ResponseEntity
                .status(500)
                .body(ErrorResponse.builder()
                        .code("INTERNAL_SERVER_ERROR")
                        .message("서버 내부 오류가 발생했습니다.")
                        .build());
    }
}