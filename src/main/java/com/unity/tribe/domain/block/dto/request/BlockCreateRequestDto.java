package com.unity.tribe.domain.block.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class BlockCreateRequestDto {

    @NotBlank(message = "차단할 사용자 ID는 필수입니다.")
    @Pattern(regexp = "^[0123456789ABCDEFGHJKMNPQRSTVWXYZ]{26}$", message = "유효하지 않은 ULID 형식입니다.")
    private String blockedUserId;
}