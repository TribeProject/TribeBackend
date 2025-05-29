package com.unity.tribe.domain.auth.service;

import org.springframework.stereotype.Service;

import com.unity.tribe.domain.auth.dto.request.LoginRequestDto;
import com.unity.tribe.domain.auth.dto.response.LoginResponseDto;


@Service
public interface AuthService {

    LoginResponseDto ssoLogin(LoginRequestDto loginRequestDto);

    void validateToken(String userId, String token);
}
