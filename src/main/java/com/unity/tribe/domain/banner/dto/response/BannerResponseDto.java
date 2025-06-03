package com.unity.tribe.domain.banner.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BannerResponseDto {

    private int bannerId;
    private String title;
    private String image;
    private String linkUrl;
    private int priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
