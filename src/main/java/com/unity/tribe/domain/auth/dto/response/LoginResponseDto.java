package com.unity.tribe.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto<T> {

    private String provider;
    private boolean isRegistered;
    private T data;

}
