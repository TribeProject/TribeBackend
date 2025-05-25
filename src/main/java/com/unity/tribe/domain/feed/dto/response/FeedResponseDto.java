package com.unity.tribe.domain.feed.dto.response;

import java.time.LocalDateTime;

import com.unity.tribe.domain.feed.entity.FeedEntity.FeedStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedResponseDto {
    private Long feedId;
    private String groupId;
    private String userId;
    private String feedType;
    private String content;
    private String image;
    private FeedStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}