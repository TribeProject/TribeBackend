package com.unity.tribe.domain.admin.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class AdminResponseDto {

    private String userId;
    private String adminId;
    private String adminName;
    private String adminEmail;
    private String status;
    private String creatorId;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private String lastLoginIp;


}
