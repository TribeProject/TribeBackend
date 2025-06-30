package com.unity.tribe.domain.group.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {
    private String userId;
    private String nickname;
    private String role;
    private LocalDateTime joinedAt;
}