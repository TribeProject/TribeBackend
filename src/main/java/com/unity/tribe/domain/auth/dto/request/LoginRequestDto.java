package com.unity.tribe.domain.auth.dto.request;

public record LoginRequestDto(
        String provider,
        boolean success,
        String accessToken) {
}
