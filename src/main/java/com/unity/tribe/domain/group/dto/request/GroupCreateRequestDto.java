package com.unity.tribe.domain.group.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class GroupCreateRequestDto {

    @NotBlank(message = "그룹명은 필수입니다.")
    @Size(min = 2, max = 20, message = "그룹명은 2자 이상 20자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "그룹 설명은 필수입니다.")
    @Size(min = 10, max = 1000, message = "그룹 설명은 10자 이상 1000자 이하여야 합니다.")
    private String description;

    @NotNull(message = "카테고리는 필수입니다.")
    private Long categoryId;

    @NotNull(message = "최대 멤버 수는 필수입니다.")
    @Min(value = 2, message = "최대 멤버 수는 2명 이상이어야 합니다.")
    @Max(value = 100, message = "최대 멤버 수는 100명 이하여야 합니다.")
    private Integer maxMembers;

    @Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif|webp)$", message = "올바른 이미지 URL 형식이어야 합니다.")
    private String imageUrl;
}