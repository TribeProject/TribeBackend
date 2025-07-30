package com.unity.tribe.domain.auth.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.common.util.JwtUtil;
import com.unity.tribe.domain.auth.docs.DevTokenApi;
import com.unity.tribe.domain.auth.dto.request.DevTokenRequestDto;
import com.unity.tribe.domain.auth.dto.response.TokenResponseDto;
import com.unity.tribe.domain.user.entity.UserEntity;
import com.unity.tribe.domain.user.repository.UserRepository;

import de.huxhorn.sulky.ulid.ULID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ULID ulid = new ULID();

    /**
     * 개발용 JWT 토큰 발급 및 임시 사용자 생성
     * 
     * @param request 토큰 발급 요청 데이터
     * @return JWT 토큰 정보
     */
    @PostMapping("/dev/token")
    @Operation(summary = "개발용 토큰 발급", description = "테스트 및 개발용으로 JWT 토큰을 발급합니다. 동일한 닉네임이 존재하면 기존 사용자의 토큰을 발급하고, 없으면 새로운 임시 사용자를 생성합니다.")
    @DevTokenApi
    @Transactional
    public ResponseEntity<ApiResponseDto<TokenResponseDto>> generateDevToken(
            @Valid @RequestBody DevTokenRequestDto request) {

        String userId;
        UserEntity user;

        // 기존 사용자가 있는지 닉네임 확인
        var existingUser = userRepository.findByNickname(request.getNickname());

        if (existingUser.isPresent()) {
            // 기존 사용자가 있으면 해당 사용자 ID 사용
            user = existingUser.get();
            userId = user.getUserId();
        } else {
            // 새로운 사용자 생성
            userId = ulid.nextULID();

            user = new UserEntity();
            user.setUserId(userId);
            user.setNickname(request.getNickname());
            user.setName("개발용사용자_" + request.getNickname());
            user.setJoinType("DEV");
            user.setEmail("dev_" + userId + "@test.com");
            user.setStatus("ACTIVE");
            user.setTermsAgreed((byte) 1);
            user.setPrivacyAgreed((byte) 1);
            user.setMarketingAgreed((byte) 0);
            user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            // 데이터베이스에 새 사용자 저장
            userRepository.save(user);
        }

        // JWT 토큰 생성
        String accessToken = jwtUtil.generateToken(userId);

        TokenResponseDto tokenResponse = TokenResponseDto.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresIn(86400) // 24시간 (초 단위)
                .userId(userId)
                .nickname(user.getNickname())
                .build();

        String message = existingUser.isPresent() ? "기존 사용자의 개발용 토큰이 성공적으로 발급되었습니다."
                : "새로운 개발용 사용자가 생성되고 토큰이 성공적으로 발급되었습니다.";

        return ResponseEntity.ok(ApiResponseDto.success(message, tokenResponse));
    }
}
