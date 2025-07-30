package com.unity.tribe.domain.group.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.unity.tribe.domain.group.entity.GroupCategory.GroupCategoryCode;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupCreateRequestDto {

    // === 기본 모임 정보 ===
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
    @Pattern(regexp = "ONLINE|OFFLINE", message = "모임 방식은 ONLINE, OFFLINE 중 하나여야 합니다.")
    private String meetingType;

    private String locationAddress;
    private String locationLatitude;
    private String locationLongitude;

    // === 모집 조건 ===
    @NotNull(message = "최대 참여 인원은 필수입니다.")
    @Min(value = 2, message = "최대 참여 인원은 2명 이상이어야 합니다.")
    @Max(value = 100, message = "최대 참여 인원은 100명 이하여야 합니다.")
    private Integer participants;

    @Pattern(regexp = "NONE|MALE|FEMALE", message = "성별 제한은 NONE, MALE, FEMALE 중 하나여야 합니다.")
    private String genderRestriction = "NONE";

    @Min(value = 0, message = "최소 나이는 0세 이상이어야 합니다.")
    @Max(value = 120, message = "최소 나이는 120세 이하여야 합니다.")
    private Integer minAge;

    @Min(value = 0, message = "최대 나이는 0세 이상이어야 합니다.")
    @Max(value = 120, message = "최대 나이는 120세 이하여야 합니다.")
    private Integer maxAge;

    @Pattern(regexp = "^(https?://).*\\.(jpg|jpeg|png|gif|webp)$", message = "올바른 이미지 URL 형식이어야 합니다.")
    private String thumbnail;

    private LocalDateTime expiredAt;

    // === 목표 설정 정보 ===
    @Valid
    private GoalInfo goalInfo;

    @Getter
    @Setter
    public static class GoalInfo {
        @NotBlank(message = "목표명은 필수입니다.")
        @Size(min = 2, max = 100, message = "목표명은 2자 이상 100자 이하여야 합니다.")
        private String title;

        @Size(max = 500, message = "목표 설명은 500자 이하여야 합니다.")
        private String description;

        @NotNull(message = "목표 달성 기준값은 필수입니다.")
        @Min(value = 1, message = "목표 달성 기준값은 1 이상이어야 합니다.")
        private Integer targetValue;

        @NotNull(message = "시작일은 필수입니다.")
        private LocalDate startDate;

        @NotNull(message = "종료일은 필수입니다.")
        private LocalDate endDate;

        @Valid
        @NotNull(message = "인증 규칙은 필수입니다.")
        private CertificationInfo certificationRule;
    }

    @Getter
    @Setter
    public static class CertificationInfo {
        @NotBlank(message = "인증 주기는 필수입니다.")
        @Pattern(regexp = "DAILY|WEEKLY|MONTHLY", message = "인증 주기는 DAILY, WEEKLY, MONTHLY 중 하나여야 합니다.")
        private String certificationFrequency;

        @NotNull(message = "텍스트 인증 필수 여부는 필수입니다.")
        private Boolean textRequired = false;

        @NotNull(message = "이미지 인증 필수 여부는 필수입니다.")
        private Boolean imageRequired = true;

        @Size(max = 200, message = "인증 내용 설명은 200자 이하여야 합니다.")
        private String content;

        // 주간 인증 관련 (WEEKLY인 경우 필수)
        @Min(value = 1, message = "주간 인증 횟수는 1회 이상이어야 합니다.")
        @Max(value = 7, message = "주간 인증 횟수는 7회 이하여야 합니다.")
        private Integer weeklyCount;

        private List<@Pattern(regexp = "MON|TUE|WED|THU|FRI|SAT|SUN", message = "올바른 요일 형식이어야 합니다.") String> weeklyDays;

        // 월간 인증 관련 (MONTHLY인 경우 필수)
        private List<@Min(value = 1, message = "월간 인증 일자는 1일 이상이어야 합니다.") @Max(value = 31, message = "월간 인증 일자는 31일 이하여야 합니다.") Integer> monthlyDays;

        private LocalDate goalDate;
    }

    // 유효성 검증 메서드
    @AssertTrue(message = "최소 나이가 최대 나이보다 클 수 없습니다.")
    public boolean isValidAgeRange() {
        if (minAge == null || maxAge == null) {
            return true;
        }
        return minAge <= maxAge;
    }

    @AssertTrue(message = "오프라인 모임의 경우 위치 정보는 필수입니다.")
    public boolean isValidLocation() {
        if ("OFFLINE".equals(meetingType)) {
            return locationAddress != null && !locationAddress.trim().isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "목표 정보 유효성 검증 실패")
    public boolean isValidGoalInfo() {
        if (goalInfo == null) {
            return true; // 목표 정보는 선택적
        }

        // 시작일이 종료일보다 이후일 수 없음
        if (goalInfo.getStartDate() != null && goalInfo.getEndDate() != null) {
            if (goalInfo.getStartDate().isAfter(goalInfo.getEndDate())) {
                return false;
            }
        }

        // 인증 규칙 검증
        CertificationInfo cert = goalInfo.getCertificationRule();
        if (cert != null) {
            // 텍스트나 이미지 중 최소 하나는 필수
            if (!cert.getTextRequired() && !cert.getImageRequired()) {
                return false;
            }

            // 주간 인증 검증
            if ("WEEKLY".equals(cert.getCertificationFrequency())) {
                if (cert.getWeeklyCount() == null || cert.getWeeklyDays() == null) {
                    return false;
                }
                if (!cert.getWeeklyCount().equals(cert.getWeeklyDays().size())) {
                    return false;
                }
            }

            // 월간 인증 검증
            if ("MONTHLY".equals(cert.getCertificationFrequency())) {
                if (cert.getMonthlyDays() == null || cert.getMonthlyDays().isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }
}