package com.unity.tribe.domain.hide.dto.request;

import com.unity.tribe.domain.hide.entity.HideEntity.TargetType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class HideCreateRequestDto {

    @NotNull(message = "숨김 대상 타입은 필수입니다.")
    private TargetType targetType;

    @NotBlank(message = "숨김 대상 ID는 필수입니다.")
    private String targetId;
}