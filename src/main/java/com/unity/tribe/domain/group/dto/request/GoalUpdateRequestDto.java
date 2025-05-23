package com.unity.tribe.domain.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class GoalUpdateRequestDto {

    @NotBlank(message = "목표 제목은 필수입니다.")
    @Size(min = 2, max = 100, message = "목표 제목은 2자 이상 100자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "목표 설명은 필수입니다.")
    @Size(max = 1000, message = "목표 설명은 1000자 이하여야 합니다.")
    private String description;

    @NotNull(message = "목표 시작일은 필수입니다.")
    private String startDate;

    @NotNull(message = "목표 종료일은 필수입니다.")
    private String endDate;
}