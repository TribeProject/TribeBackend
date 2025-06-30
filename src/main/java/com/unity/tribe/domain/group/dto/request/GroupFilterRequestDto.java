package com.unity.tribe.domain.group.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 그룹 목록 필터링을 위한 요청 DTO
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupFilterRequestDto {

    /**
     * 검색 키워드 (제목, 설명에서 검색)
     */
    @Size(min = 1, max = 100, message = "검색 키워드는 1자 이상 100자 이하여야 합니다.")
    private String keyword;

    /**
     * 그룹 상태 (WAITING, ONGOING, FINISHED, DISBANDED)
     */
    @Pattern(regexp = "^(WAITING|ONGOING|FINISHED|DISBANDED)$", message = "그룹 상태는 WAITING, ONGOING, FINISHED, DISBANDED 중 하나여야 합니다.")
    private String status;

    /**
     * 모임 시작일 필터 (이 날짜 이후)
     */
    private LocalDate startDate;

    /**
     * 모임 종료일 필터 (이 날짜 이전)
     */
    private LocalDate endDate;

    /**
     * 지역 필터 (여러 지역 가능)
     * e.g. ["서울", "경기", "인천"]
     */
    @Size(max = 10, message = "지역 필터는 최대 10개까지 가능합니다.")
    private List<String> regions;

    /**
     * 최소 나이 제한
     */
    @Min(value = 1, message = "최소 나이는 1 이상이어야 합니다.")
    @Max(value = 100, message = "최소 나이는 100 이하여야 합니다.")
    private Integer minAge;

    /**
     * 최대 나이 제한
     */
    @Min(value = 1, message = "최대 나이는 1 이상이어야 합니다.")
    @Max(value = 100, message = "최대 나이는 100 이하여야 합니다.")
    private Integer maxAge;

    /**
     * 성별 제한 (ALL, MALE, FEMALE)
     */
    @Pattern(regexp = "^(NONE|MALE|FEMALE)$", message = "성별 제한은 NONE, MALE, FEMALE 중 하나여야 합니다.")
    private String genderRestriction;

    /**
     * 최소 참여 인원
     */
    @Min(value = 1, message = "최소 참여 인원은 1 이상이어야 합니다.")
    @Max(value = 100, message = "최소 참여 인원은 100 이하여야 합니다.")
    private Integer minParticipants;

    /**
     * 최대 참여 인원
     */
    @Min(value = 1, message = "최대 참여 인원은 1 이상이어야 합니다.")
    @Max(value = 100, message = "최대 참여 인원은 100 이하여야 합니다.")
    private Integer maxParticipants;

    /**
     * 모임 주제/카테고리 (여러 카테고리 가능)
     */
    @Size(max = 5, message = "카테고리 필터는 최대 5개까지 가능합니다.")
    private List<String> categories;

    /**
     * 필터가 비어있는지 확인
     */
    public boolean isEmpty() {
        return keyword == null && status == null && startDate == null && endDate == null &&
                (regions == null || regions.isEmpty()) && minAge == null && maxAge == null &&
                genderRestriction == null && minParticipants == null && maxParticipants == null &&
                (categories == null || categories.isEmpty());
    }

    /**
     * 키워드 검색이 있는지 확인
     */
    public boolean hasKeyword() {
        return keyword != null && !keyword.trim().isEmpty();
    }

    /**
     * 날짜 필터가 있는지 확인
     */
    public boolean hasDateFilter() {
        return startDate != null || endDate != null;
    }

    /**
     * 지역 필터가 있는지 확인
     */
    public boolean hasRegionFilter() {
        return regions != null && !regions.isEmpty();
    }

    /**
     * 나이 필터가 있는지 확인
     */
    public boolean hasAgeFilter() {
        return minAge != null || maxAge != null;
    }

    /**
     * 참여 인원 필터가 있는지 확인
     */
    public boolean hasParticipantsFilter() {
        return minParticipants != null || maxParticipants != null;
    }

    /**
     * 카테고리 필터가 있는지 확인
     */
    public boolean hasCategoryFilter() {
        return categories != null && !categories.isEmpty();
    }
}