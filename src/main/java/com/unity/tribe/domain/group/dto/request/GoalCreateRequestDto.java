package com.unity.tribe.domain.group.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoalCreateRequestDto {

    @NotBlank(message = "목표 제목은 필수입니다.")
    @Size(min = 2, max = 100, message = "목표 제목은 2자 이상 100자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "목표 설명은 필수입니다.")
    @Size(min = 10, max = 1000, message = "목표 설명은 10자 이상 1000자 이하여야 합니다.")
    private String description;

    @Min(value = 1, message = "목표값은 1 이상이어야 합니다.")
    private Integer targetValue;

    @Min(value = 1, message = "인증 규칙 ID는 1 이상이어야 합니다.")
    private Integer certificationRuleId;

    @NotNull(message = "시작일은 필수입니다.")
    private LocalDate startDate;

    @NotNull(message = "종료일은 필수입니다.")
    @Future(message = "종료일은 현재 날짜보다 미래여야 합니다.")
    private LocalDate endDate;

    @Schema(description = "인증 규칙", required = true)
    @NotNull(message = "인증 규칙은 필수입니다.")
    @Valid
    private CertificationRuleRequestDto certificationRule;
}