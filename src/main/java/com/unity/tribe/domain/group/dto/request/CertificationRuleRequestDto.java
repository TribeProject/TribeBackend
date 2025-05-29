package com.unity.tribe.domain.group.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRuleRequestDto {

    @NotBlank(message = "인증 빈도는 필수입니다.")
    @Pattern(regexp = "^(DAILY|WEEKLY|MONTHLY)$", message = "인증 빈도는 DAILY, WEEKLY, MONTHLY 중 하나여야 합니다.")
    private String certificationFrequency;

    @NotNull(message = "텍스트 필수 여부는 필수입니다.")
    private boolean textRequired;

    @NotNull(message = "이미지 필수 여부는 필수입니다.")
    private boolean imageRequired;

    @Min(value = 1, message = "주간 인증 횟수는 1 이상이어야 합니다.")
    @Max(value = 7, message = "주간 인증 횟수는 7 이하여야 합니다.")
    private Integer weeklyCount;

    @Size(max = 7, message = "주간 인증 요일은 최대 7개까지 가능합니다.")
    private List<String> weeklyDays;

    @Size(max = 31, message = "월간 인증 일자는 최대 31개까지 가능합니다.")
    private List<String> monthlyDays;

    private LocalDate goalDate;

    @Size(max = 500, message = "인증 규칙 내용은 500자 이하여야 합니다.")
    private String content;
}