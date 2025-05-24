package com.unity.tribe.domain.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCreateRequestDto {

    private String reasonType;
    private String targetUserId;
    private String targetCommentId;
    private String targetFeedId;
    private String targetType;
}