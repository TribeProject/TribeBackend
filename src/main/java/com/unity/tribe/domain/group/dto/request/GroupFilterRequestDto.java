package com.unity.tribe.domain.group.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GroupFilterRequestDto {

    // 키워드 검색 (제목, 설명)
    private String keyword;

    // 그룹 상태 필터
    private String status;

    // 날짜 범위 필터
    private LocalDate startDate;
    private LocalDate endDate;

    // 지역 필터
    private List<String> regions;

    // 나이 제한 필터
    private Integer minAge;
    private Integer maxAge;

    // 성별 제한 필터
    private String genderRestriction;

    // 참여 인원 필터
    private Integer minParticipants;
    private Integer maxParticipants;

    // 카테고리 필터
    private List<String> categories;

    // 모임 유형 필터
    private String groupType; // MISSION, CONTINUOUS

    // 모임 방식 필터
    private String meetingType; // ONLINE, OFFLINE

    // 호스트 필터
    private String hostId;

    // 정렬 옵션
    private String sortBy; // createdAt, participants, title, status
    private String sortDirection; // ASC, DESC

    /**
     * 필터 조건이 비어있는지 확인
     * 
     * @return 모든 필터 조건이 비어있으면 true
     */
    public boolean isEmpty() {
        return (keyword == null || keyword.trim().isEmpty()) &&
                (status == null || status.trim().isEmpty()) &&
                startDate == null &&
                endDate == null &&
                (regions == null || regions.isEmpty()) &&
                minAge == null &&
                maxAge == null &&
                (genderRestriction == null || genderRestriction.trim().isEmpty()) &&
                minParticipants == null &&
                maxParticipants == null &&
                (categories == null || categories.isEmpty()) &&
                (groupType == null || groupType.trim().isEmpty()) &&
                (meetingType == null || meetingType.trim().isEmpty()) &&
                (hostId == null || hostId.trim().isEmpty());
    }

    /**
     * 키워드 검색인지 확인
     * 
     * @return 키워드가 있으면 true
     */
    public boolean hasKeyword() {
        return keyword != null && !keyword.trim().isEmpty();
    }

    /**
     * 지역 필터가 있는지 확인
     * 
     * @return 지역 필터가 있으면 true
     */
    public boolean hasRegions() {
        return regions != null && !regions.isEmpty();
    }

    /**
     * 카테고리 필터가 있는지 확인
     * 
     * @return 카테고리 필터가 있으면 true
     */
    public boolean hasCategories() {
        return categories != null && !categories.isEmpty();
    }

    /**
     * 날짜 범위 필터가 있는지 확인
     * 
     * @return 날짜 범위 필터가 있으면 true
     */
    public boolean hasDateRange() {
        return startDate != null || endDate != null;
    }

    /**
     * 나이 필터가 있는지 확인
     * 
     * @return 나이 필터가 있으면 true
     */
    public boolean hasAgeFilter() {
        return minAge != null || maxAge != null;
    }

    /**
     * 참여 인원 필터가 있는지 확인
     * 
     * @return 참여 인원 필터가 있으면 true
     */
    public boolean hasParticipantsFilter() {
        return minParticipants != null || maxParticipants != null;
    }

    /**
     * 빌더에서 trim된 문자열을 설정하는 정적 팩토리 메서드
     */
    public static GroupFilterRequestDtoBuilder safeBuilder() {
        return new GroupFilterRequestDtoBuilder() {
            @Override
            public GroupFilterRequestDtoBuilder keyword(String keyword) {
                return super.keyword(keyword != null ? keyword.trim() : null);
            }

            @Override
            public GroupFilterRequestDtoBuilder status(String status) {
                return super.status(status != null ? status.trim().toUpperCase() : null);
            }

            @Override
            public GroupFilterRequestDtoBuilder genderRestriction(String genderRestriction) {
                return super.genderRestriction(
                        genderRestriction != null ? genderRestriction.trim().toUpperCase() : null);
            }

            @Override
            public GroupFilterRequestDtoBuilder groupType(String groupType) {
                return super.groupType(groupType != null ? groupType.trim().toUpperCase() : null);
            }

            @Override
            public GroupFilterRequestDtoBuilder meetingType(String meetingType) {
                return super.meetingType(meetingType != null ? meetingType.trim().toUpperCase() : null);
            }

            @Override
            public GroupFilterRequestDtoBuilder hostId(String hostId) {
                return super.hostId(hostId != null ? hostId.trim() : null);
            }

            @Override
            public GroupFilterRequestDtoBuilder sortBy(String sortBy) {
                return super.sortBy(sortBy != null ? sortBy.trim() : null);
            }

            @Override
            public GroupFilterRequestDtoBuilder sortDirection(String sortDirection) {
                return super.sortDirection(sortDirection != null ? sortDirection.trim().toUpperCase() : null);
            }
        };
    }
}