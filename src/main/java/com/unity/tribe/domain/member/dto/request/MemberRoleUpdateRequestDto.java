package com.unity.tribe.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRoleUpdateRequestDto {

    @NotBlank(message = "역할은 필수입니다.")
    @Pattern(regexp = "^(MEMBER|HOST)$", message = "역할은 MEMBER, HOST 중 하나여야 합니다.")
    private String role;
}