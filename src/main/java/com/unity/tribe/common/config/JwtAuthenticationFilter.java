package com.unity.tribe.common.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.unity.tribe.common.util.JwtUtil;
import com.unity.tribe.domain.user.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = resolveToken(request);
            log.debug("JWT Token from request: {}",
                    token != null ? "****" + token.substring(Math.max(0, token.length() - 4)) : "null");

            if (StringUtils.hasText(token)) {
                // JwtUtil 사용하여 토큰 검증
                if (jwtUtil.validateToken(token)) {
                    String userId = jwtUtil.getUserIdFromToken(token);
                    log.debug("Valid token for user: {}", userId);

                    try {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

                        // 인증 객체 생성 및 SecurityContext에 설정
                        Authentication auth = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        log.debug("Authentication set for user: {} with authorities: {}", userId,
                                userDetails.getAuthorities());
                    } catch (Exception e) {
                        log.error("Failed to load user details for userId: {}", userId, e);
                        SecurityContextHolder.clearContext();
                    }
                } else {
                    log.debug("Invalid JWT token");
                }
            } else {
                log.debug("No JWT token found in request");
            }
        } catch (Exception e) {
            log.error("JWT Authentication error: ", e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
