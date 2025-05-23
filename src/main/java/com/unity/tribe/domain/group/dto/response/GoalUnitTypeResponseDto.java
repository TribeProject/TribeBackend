package com.unity.tribe.domain.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoalUnitTypeResponseDto {

    private Integer unitTypeId;
    private String title;

    public static GoalUnitTypeResponseDto from(com.unity.tribe.domain.group.entity.GoalUnitTypeEntity entity) {
        return GoalUnitTypeResponseDto.builder()
                .unitTypeId(entity.getUnitTypeId())
                .title(entity.getTitle())
                .build();
    }
}
