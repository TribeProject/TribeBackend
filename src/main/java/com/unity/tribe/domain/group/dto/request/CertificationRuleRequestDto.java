package com.unity.tribe.domain.group.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRuleRequestDto {

    private String certificationFrequency;
    private boolean textRequired;
    private boolean imageRequired;
    private Integer weeklyCount;
    private List<String> weeklyDays;
    private List<String> monthlyDays;
    private LocalDate goalDate;
    private String content;
}