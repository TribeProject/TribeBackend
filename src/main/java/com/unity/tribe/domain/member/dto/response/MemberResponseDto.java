package com.unity.tribe.domain.member.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {
    private Long memberId;
    private String userId;
    private String nickname;
    private String profileImg;
    private String role; // "MEMBER" or "HOST"
    private String status; // "ACTIVE", "EXITED"
    private LocalDateTime joinedAt;
}