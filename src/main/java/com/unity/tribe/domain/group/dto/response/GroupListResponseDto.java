package com.unity.tribe.domain.group.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GroupListResponseDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Long categoryId;
    private String categoryName;
    private Integer currentMembers;
    private Integer maxMembers;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}