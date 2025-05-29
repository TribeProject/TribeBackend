package com.unity.tribe.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.unity.tribe.common.config.security.JwtTokenProvider;
import com.unity.tribe.domain.auth.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        //.requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
                                "/api-docs/**", "/swagger-resources/**", "/wears/**",
                                "/swagger-ui/index.html") // Swagger
                                                          // UI 경로
                                                          // 허용
                        .permitAll().requestMatchers("/actuator/**").permitAll() // actuator 경로 허용
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, authService), UsernamePasswordAuthenticationFilter.class)
                .formLogin(formLogin -> formLogin.permitAll());
        return http.build();
    }

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;
}
