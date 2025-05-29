package com.unity.tribe.domain.feed.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class FeedCreateRequestDto {

    @NotBlank(message = "그룹 ID는 필수입니다.")
    @Pattern(regexp = "^[0123456789ABCDEFGHJKMNPQRSTVWXYZ]{26}$", message = "그룹 ID는 ULID 형식이어야 합니다.")
    private String groupId;

    @NotBlank(message = "피드 타입은 필수입니다.")
    @Pattern(regexp = "^(CERTIFICATION|NORMAL)$", message = "피드 타입은 CERTIFICATION, NORMAL 중 하나여야 합니다.")
    private String feedType;

    @Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif|webp)$", message = "올바른 이미지 URL 형식이어야 합니다.")
    private String image;

    @Size(min = 1, max = 2000, message = "피드 내용은 1자 이상 2000자 이하여야 합니다.")
    private String content;
}