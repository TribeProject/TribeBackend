package com.unity.tribe.domain.group.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class GoalUpdateRequestDto {

    @Size(min = 2, max = 100, message = "목표 제목은 2자 이상 100자 이하여야 합니다.")
    private String title;

    @Size(min = 10, max = 1000, message = "목표 설명은 10자 이상 1000자 이하여야 합니다.")
    private String description;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "시작일은 YYYY-MM-DD 형식이어야 합니다.")
    private String startDate;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "종료일은 YYYY-MM-DD 형식이어야 합니다.")
    private String endDate;
}