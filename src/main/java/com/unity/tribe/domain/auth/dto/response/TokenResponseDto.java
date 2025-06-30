package com.unity.tribe.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDto {

    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private String userId;
    private String nickname;
}