package com.unity.tribe.domain.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class GroupUpdateRequestDto {

    @NotBlank(message = "그룹 이름은 필수입니다.")
    @Size(min = 2, max = 50, message = "그룹 이름은 2자 이상 50자 이하여야 합니다.")
    private String name;

    @NotBlank(message = "그룹 설명은 필수입니다.")
    @Size(max = 500, message = "그룹 설명은 500자 이하여야 합니다.")
    private String description;

    @NotNull(message = "카테고리는 필수입니다.")
    private Long categoryId;

    @NotNull(message = "최대 인원은 필수입니다.")
    private Integer maxMembers;

    @NotBlank(message = "그룹 이미지는 필수입니다.")
    private String imageUrl;
}