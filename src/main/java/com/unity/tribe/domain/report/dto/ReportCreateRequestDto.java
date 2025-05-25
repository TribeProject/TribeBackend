package com.unity.tribe.domain.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCreateRequestDto {

    @NotBlank(message = "신고 사유는 필수입니다.")
    private String reasonType;

    @Pattern(regexp = "^[0123456789ABCDEFGHJKMNPQRSTVWXYZ]{26}$", message = "대상 사용자 ID는 ULID 형식이어야 합니다.")
    private String targetUserId;

    private String targetCommentId;

    @Pattern(regexp = "^[0123456789ABCDEFGHJKMNPQRSTVWXYZ]{26}$", message = "대상 피드 ID는 ULID 형식이어야 합니다.")
    private String targetFeedId;

    @NotBlank(message = "신고 대상 타입은 필수입니다.")
    @Pattern(regexp = "^(FEED|COMMENT|USER)$", message = "신고 대상 타입은 FEED, COMMENT, USER 중 하나여야 합니다.")
    private String targetType;
}