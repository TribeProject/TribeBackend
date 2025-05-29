package com.unity.tribe.domain.feed.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class FeedUpdateRequestDto {

    @Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif|webp)$", message = "올바른 이미지 URL 형식이어야 합니다.")
    private String image;

    @Size(min = 1, max = 2000, message = "피드 내용은 1자 이상 2000자 이하여야 합니다.")
    private String content;
}