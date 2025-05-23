package com.unity.tribe.domain.group.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupResponseDto {
    private String groupId;
    private String title;
    private String description;
    private String status;
    private String hostId;
    private Integer participants;
}
