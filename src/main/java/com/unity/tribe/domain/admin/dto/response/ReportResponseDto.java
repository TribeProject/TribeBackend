package com.unity.tribe.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReportResponseDto {

    private Long id;
    private String userId;
    private String targetType;
    private String targetGroupId;
    private String targetFeedId;
    private String targetUserId;
    private int reportCount;
    private String reasonType;
    private String status;
    private String createdAt;


}
