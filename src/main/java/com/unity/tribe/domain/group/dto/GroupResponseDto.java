package com.unity.tribe.domain.group.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GroupResponseDto {

    @Schema(description = "그룹 ID", example = "")
    private String groupId;
    private String hostId;
    private String categoryId;
    private String title;
    private String description;
    private String thumbnail;
    private String groupType;
    private String meetingType;
    private String locationAddress;
    private String locationLatitude;
    private String locationLongitude;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
