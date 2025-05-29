package com.unity.tribe.domain.admin.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BannerAddRequestDto {
    private String title;
    private String imagePc;
    private String imageApp;
    private String linkUrl;
    private int priority;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
