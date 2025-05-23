package com.unity.tribe.domain.group.dto.request;

import lombok.Getter;

@Getter
public class GroupUpdateRequestDto {

    private String name;
    private String description;
    private Long categoryId;
    private Integer maxMembers;
    private String imageUrl;
}