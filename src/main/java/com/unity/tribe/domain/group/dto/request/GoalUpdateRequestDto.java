package com.unity.tribe.domain.group.dto.request;

import lombok.Getter;

@Getter
public class GoalUpdateRequestDto {
    private String title;
    private String description;
    private String startDate;
    private String endDate;
}