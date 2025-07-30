package com.unity.tribe.domain.auth.service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.unity.tribe.common.config.security.JwtTokenProvider;
import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeApiException;
import com.unity.tribe.common.exception.TribeAuthenticateException;
import com.unity.tribe.common.model.enums.ExternalApiUrl;
import com.unity.tribe.common.model.enums.SsoProvider;
import com.unity.tribe.domain.auth.dto.request.LoginRequestDto;
import com.unity.tribe.domain.auth.dto.response.LoginResponseDto;
import com.unity.tribe.domain.auth.dto.response.NaverProfileResponseDto;
import com.unity.tribe.domain.auth.dto.response.TokenInfoDto;
import com.unity.tribe.domain.auth.entity.AuthTokenEntity;
import com.unity.tribe.domain.auth.repository.AuthRepository;
import com.unity.tribe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Map<String, WebClient> webClientMap;

    @Override
    public LoginResponseDto ssoLogin(LoginRequestDto loginRequestDto) {
        WebClient webClient = webClientMap.get(loginRequestDto.provider() + "WebClient");
        try {
            if (loginRequestDto.provider().toUpperCase().equals(SsoProvider.NAVER)) {
                NaverProfileResponseDto profileResponseDto = getNaverProfile(loginRequestDto.accessToken(), webClient);

                return userRepository.findByEmail(profileResponseDto.getProfile().getEmail())
                        .map(exist -> {
                            TokenInfoDto tokenInfoDto = createToken(exist.getUserId());
                            storeToken(tokenInfoDto);
                            return LoginResponseDto.builder()
                                    .provider("naver")
                                    .isRegistered(true)
                                    .data(tokenInfoDto)
                                    .build();
                        })
                        .orElseGet(() -> LoginResponseDto.builder()
                                .provider("naver")
                                .isRegistered(false)
                                .data(profileResponseDto)
                                .build());
            }
        } catch (Exception e) {
            throw new TribeAuthenticateException(ErrorCode.NAVER_LOGIN_FAILED);
        }

        // 현재 naver 로그인만 있기 때문에 임시 예외처리
        throw new TribeAuthenticateException(ErrorCode.NAVER_LOGIN_FAILED);

    }

    private void storeToken(TokenInfoDto tokenInfoDto) {
        AuthTokenEntity authToken = AuthTokenEntity.builder()
                .userId(tokenInfoDto.getUserId())
                .accessToken(tokenInfoDto.getAccessToken())
                .refreshToken(tokenInfoDto.getRefreshToken())
                .createdAt(LocalDateTime.now())
                .build();

        authRepository.save(authToken);
    }

    @Override
    public void validateToken(String userId, String token) {

        if (!userRepository.existsById(userId)) {
            throw new TribeAuthenticateException(ErrorCode.USER_NOT_FOUND);
        }

        AuthTokenEntity authToken = authRepository.findByUserId(userId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.INVALID_TOKEN));
        if (!token.equals(authToken.getAccessToken())) {
            throw new TribeApiException(ErrorCode.INVALID_TOKEN);
        }

    }

    private TokenInfoDto createToken(String userId) {
        return jwtTokenProvider.createToken(userId);
    }

    private NaverProfileResponseDto getNaverProfile(String naverAccessToken, WebClient naverWebClient) {
        try {
            NaverProfileResponseDto profileResponseDto = naverWebClient.get()
                    .uri(ExternalApiUrl.GET_NAVER_PROFILE.getApi())
                    .header(AUTHORIZATION, "Bearer " + naverAccessToken)
                    .retrieve()
                    .bodyToMono(NaverProfileResponseDto.class)
                    .block();
            return profileResponseDto;
        } catch (Exception e) {
            throw new TribeApiException(ErrorCode.NAVER_LOGIN_FAILED);
        }
    }
}
