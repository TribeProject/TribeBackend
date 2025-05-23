package com.unity.tribe.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {
    private int status;
    private String message;
    private boolean result;
    private T data;

    public static <T> ApiResponseDto<T> success(T data) {
        return new ApiResponseDto<>(200, "요청이 성공했습니다.", true, data);
    }

    public static <T> ApiResponseDto<T> success(String message, T data) {
        return new ApiResponseDto<>(200, message, true, data);
    }

    public static <T> ApiResponseDto<T> fail(String message, int status) {
        return new ApiResponseDto<>(status, message, false, null);
    }

    public static <T> ApiResponseDto<T> fail(String message, int status, T data) {
        return new ApiResponseDto<>(status, message, false, data);
    }
}