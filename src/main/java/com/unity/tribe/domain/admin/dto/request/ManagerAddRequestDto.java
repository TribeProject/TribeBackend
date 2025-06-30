package com.unity.tribe.domain.admin.dto.request;

import lombok.Getter;

@Getter
public class ManagerAddRequestDto {

    private String adminId;
    private String adminName;
    private String adminEmail;
    private String phoneNumber;
    private String status;
}
