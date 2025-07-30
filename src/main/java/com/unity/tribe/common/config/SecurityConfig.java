package com.unity.tribe.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.unity.tribe.common.config.security.JwtTokenProvider;
import com.unity.tribe.common.util.JwtUtil;
import com.unity.tribe.domain.user.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring Security Filter Chain");

        http
                // CSRF 보호 비활성화
                .csrf(csrf -> csrf.disable())

                // 세션을 사용하지 않음
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 폼 로그인 비활성화
                .formLogin(form -> form.disable())

                // HTTP Basic 인증 비활성화
                .httpBasic(basic -> basic.disable())

                // 요청별 인증 설정
                .authorizeHttpRequests(authorize -> authorize
                        // Swagger UI 관련 경로 허용
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/swagger-ui/index.html"
                        ).permitAll()

                        // === 인증 불필요 허용 경로 설정 ===
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/v1/auth/dev/token").permitAll()
                        .requestMatchers("/v1/auth/login", "/v1/auth/register").permitAll()
                        .requestMatchers("/v1/banner/**").permitAll()
                        .requestMatchers("/v1/test/**").permitAll()

                        // === 인증 필요 금지 경로 설정 ===
                        .requestMatchers("/v1/admin/**").authenticated()

                        // 나머지 모든 요청
                        .anyRequest().authenticated()
                )

                // JWT 인증 필터
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtUtil, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class
                );

        log.info("Security Filter Chain configured successfully");
        return http.build();
    }
}
