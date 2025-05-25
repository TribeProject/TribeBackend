package com.unity.tribe.domain.feed.dto.request;

import lombok.Getter;

@Getter
public class FeedCreateRequestDto {
    private String groupId;
    private String feedType;
    private String image;
    private String content;
}