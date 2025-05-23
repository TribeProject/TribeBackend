package com.unity.tribe.domain.group.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GoalResponseDto {
    private Long id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}