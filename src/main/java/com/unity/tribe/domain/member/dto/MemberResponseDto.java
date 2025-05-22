package com.unity.tribe.domain.member.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {

    private String memberId;
    private String userId;
    private String groupId;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private boolean status;

}
