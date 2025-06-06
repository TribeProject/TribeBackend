package com.unity.tribe.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
