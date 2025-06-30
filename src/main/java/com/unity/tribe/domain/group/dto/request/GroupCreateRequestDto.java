package com.unity.tribe.domain.group.dto.request;

import java.time.LocalDateTime;

import com.unity.tribe.domain.group.entity.GroupCategory.GroupCategoryCode;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class GroupCreateRequestDto {

    @NotBlank(message = "모임명은 필수입니다.")
    @Size(min = 2, max = 50, message = "모임명은 2자 이상 50자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "모임 설명은 필수입니다.")
    @Size(min = 10, max = 1000, message = "모임 설명은 10자 이상 1000자 이하여야 합니다.")
    private String description;

    @NotNull(message = "카테고리는 필수입니다.")
    private GroupCategoryCode categoryCode;

    @NotBlank(message = "모임 유형은 필수입니다.")
    @Pattern(regexp = "MISSION|CONTINUOUS", message = "모임 유형은 MISSION 또는 CONTINUOUS여야 합니다.")
    private String groupType;

    @NotBlank(message = "모임 방식은 필수입니다.")
    @Pattern(regexp = "ONLINE|OFFLINE|HYBRID", message = "모임 방식은 ONLINE, OFFLINE, HYBRID 중 하나여야 합니다.")
    private String meetingType;

    private String locationAddress;

    private String locationLatitude;

    private String locationLongitude;

    @NotNull(message = "최대 참여 인원은 필수입니다.")
    @Min(value = 2, message = "최대 참여 인원은 2명 이상이어야 합니다.")
    @Max(value = 100, message = "최대 참여 인원은 100명 이하여야 합니다.")
    private Integer participants;

    @Pattern(regexp = "NONE|MALE|FEMALE", message = "성별 제한은 NONE, MALE, FEMALE 중 하나여야 합니다.")
    private String genderRestriction;

    @Min(value = 0, message = "최소 나이는 0세 이상이어야 합니다.")
    @Max(value = 120, message = "최소 나이는 120세 이하여야 합니다.")
    private Integer minAge;

    @Min(value = 0, message = "최대 나이는 0세 이상이어야 합니다.")
    @Max(value = 120, message = "최대 나이는 120세 이하여야 합니다.")
    private Integer maxAge;

    @Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif|webp)$", message = "올바른 이미지 URL 형식이어야 합니다.")
    private String thumbnail;

    private LocalDateTime expiredAt;
}