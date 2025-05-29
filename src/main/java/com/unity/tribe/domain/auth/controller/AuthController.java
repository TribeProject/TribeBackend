package com.unity.tribe.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeAuthenticateException;
import com.unity.tribe.domain.auth.dto.request.LoginRequestDto;
import com.unity.tribe.domain.auth.dto.response.LoginResponseDto;
import com.unity.tribe.domain.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    @PostMapping("/sso/login")
    public ResponseEntity<LoginResponseDto> login(@RequestHeader String Authorization, @RequestBody LoginRequestDto loginRequestDto) {

        if (StringUtils.hasText(Authorization)) {
            throw new TribeAuthenticateException(ErrorCode.INVALID_TOKEN);
        }

        LoginResponseDto responseDto = authService.ssoLogin(loginRequestDto);

        return ResponseEntity.ok(responseDto);
    }

}
