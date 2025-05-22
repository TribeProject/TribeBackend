package com.unity.tribe.domain.admin.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    @Schema(description = "유저 ID")
    private String userId;
    @Schema(description = "유저명")
    private String userName;
    @Schema(description = "성별", example = "[MALE, FEMALE]")
    private String gender;
    @Schema(description = "이메일", example = "example@domain")
    private String email;
    @Schema(description = "연동 SNS", example = "[KAKAO, NAVER]")
    private String joinType;
    @Schema(description = "가입일", example = "2022-01-01 00:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "수정일", example = "2022-01-01 00:00:00")
    private LocalDateTime updatedAt;
    @Schema(description = "삭제일", example = "2022-01-01 00:00:00")
    private LocalDateTime deletedAt;
    @Schema(description = "회원상태", example = "[ACTIVE, INACTIVE, DELETED]")
    private boolean status;
}
