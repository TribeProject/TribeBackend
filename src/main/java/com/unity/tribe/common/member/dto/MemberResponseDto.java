package com.unity.tribe.common.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
