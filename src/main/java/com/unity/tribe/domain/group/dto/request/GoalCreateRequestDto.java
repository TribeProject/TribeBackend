package com.unity.tribe.domain.group.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoalCreateRequestDto {
    private String title;
    private String description;
    private Integer targetValue;
    private Integer certificationRuleId;
    private LocalDate startDate;
    private LocalDate endDate;

    @Schema(description = "인증 규칙", required = true)
    private CertificationRuleRequestDto certificationRule;
}