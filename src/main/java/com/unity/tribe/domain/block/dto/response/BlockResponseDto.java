package com.unity.tribe.domain.block.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockResponseDto {
    private Long blockId;
    private String userId;
    private BlockedUserDto blockedUser;
    private LocalDateTime createdAt;

    @Getter
    @Builder
    public static class BlockedUserDto {
        private String userId;
        private String nickname;
        private String profileImage;
    }
}