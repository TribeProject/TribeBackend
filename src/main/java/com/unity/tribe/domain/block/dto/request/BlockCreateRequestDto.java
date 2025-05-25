package com.unity.tribe.domain.block.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class BlockCreateRequestDto {

    @NotBlank(message = "차단할 사용자 ID는 필수입니다.")
    private String blockedUserId;
}