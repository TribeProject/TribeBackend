package com.unity.tribe.domain.hide.dto.response;

import java.time.LocalDateTime;

import com.unity.tribe.domain.hide.entity.HideEntity.TargetType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HideResponseDto {
    private Long hideId;
    private String userId;
    private TargetType targetType;
    private String targetId;
    private LocalDateTime createdAt;
}