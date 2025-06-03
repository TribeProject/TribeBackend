package com.unity.tribe.domain.auth.dto.request;

import lombok.Getter;


public record LoginRequestDto(
    String provider,
    boolean success,
    String accessToken
) {}
