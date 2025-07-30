package com.unity.tribe.domain.group.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeApiException;
import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.group.dto.request.CertificationRuleRequestDto;
import com.unity.tribe.domain.group.dto.request.GoalCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GoalUpdateRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupFilterRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupUpdateRequestDto;
import com.unity.tribe.domain.group.dto.response.GoalResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupDetailResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupListResponseDto;
import com.unity.tribe.domain.group.entity.CertificationRuleEntity;
import com.unity.tribe.domain.group.entity.GoalEntity;
import com.unity.tribe.domain.group.entity.GroupCategory;
import com.unity.tribe.domain.group.entity.GroupEntity;
import com.unity.tribe.domain.group.repository.CertificationRuleRepository;
import com.unity.tribe.domain.group.repository.GoalRepository;
import com.unity.tribe.domain.group.repository.GroupCategoryRepository;
import com.unity.tribe.domain.group.repository.GroupRepository;
import com.unity.tribe.domain.group.repository.MemberRepository;
import com.unity.tribe.domain.member.entity.MemberEntity;

import de.huxhorn.sulky.ulid.ULID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GoalRepository goalRepository;
    private final GroupCategoryRepository groupCategoryRepository;
    private final CertificationRuleRepository certificationRuleRepository;
    private final MemberRepository memberRepository;
    private final ULID ulid = new ULID();

    /**
     * 새로운 모임을 생성합니다.
     * 모임 정보와 함께 목표, 인증 규칙도 함께 생성할 수 있습니다.
     */
    @Override
    @Transactional
    public GroupDetailResponseDto createGroup(GroupCreateRequestDto request, String hostId) {
        log.info("Creating group with title: {} for host: {}", request.getTitle(), hostId);

        // 위치 정보 유효성 검증
        validateLocationForMeetingType(request);

        // ULID로 그룹 ID 생성
        String groupId = ulid.nextULID();
        log.info("Generated group ID: {}", groupId);

        Timestamp now = new Timestamp(System.currentTimeMillis());

        // 그룹 엔티티 생성
        GroupEntity groupEntity = GroupEntity.builder()
                .groupId(groupId)
                .hostId(hostId)
                .categoryCode(request.getCategoryCode().getCode())
                .title(request.getTitle())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .groupType(request.getGroupType())
                .meetingType(request.getMeetingType())
                .locationAddress(request.getLocationAddress())
                .locationLatitude(request.getLocationLatitude())
                .locationLongitude(request.getLocationLongitude())
                .participants(request.getParticipants())
                .genderRestriction(request.getGenderRestriction())
                .minAge(request.getMinAge())
                .maxAge(request.getMaxAge())
                .status(GroupEntity.GroupStatus.WAITING)
                .createdAt(now)
                .updatedAt(now)
                .expiredAt(request.getExpiredAt() != null ? Timestamp.valueOf(request.getExpiredAt()) : null)
                .build();

        GroupEntity savedGroup = groupRepository.save(groupEntity);
        log.info("Group created successfully with ID: {}", savedGroup.getGroupId());

        // 목표 정보가 있는 경우 별도 생성
        if (request.getGoalInfo() != null) {
            createInitialGoal(savedGroup.getGroupId(), request.getGoalInfo(), hostId);
            log.info("Initial goal created for group: {}", savedGroup.getGroupId());
        }

        // 응답 DTO 생성
        return convertToDetailResponseDto(savedGroup);
    }

    /**
     * 위치 정보 유효성 검증
     */
    private void validateLocationForMeetingType(GroupCreateRequestDto request) {
        if ("OFFLINE".equals(request.getMeetingType())) {
            if (request.getLocationAddress() == null || request.getLocationAddress().trim().isEmpty()) {
                throw new TribeApiException(ErrorCode.LOCATION_REQUIRED_FOR_OFFLINE);
            }
        }
    }

    /**
     * 초기 목표 생성
     * 목표는 여러 개 가질 수 있으므로 별도 메서드로 분리
     */
    private void createInitialGoal(String groupId, GroupCreateRequestDto.GoalInfo goalInfo, String creatorId) {
        GoalCreateRequestDto goalRequest = GoalCreateRequestDto.builder()
                .title(goalInfo.getTitle())
                .description(goalInfo.getDescription())
                .targetValue(goalInfo.getTargetValue())
                .startDate(goalInfo.getStartDate())
                .endDate(goalInfo.getEndDate())
                .certificationRule(CertificationRuleRequestDto.builder()
                        .certificationFrequency(goalInfo.getCertificationRule().getCertificationFrequency())
                        .textRequired(goalInfo.getCertificationRule().getTextRequired())
                        .imageRequired(goalInfo.getCertificationRule().getImageRequired())
                        .content(goalInfo.getCertificationRule().getContent())
                        .weeklyCount(goalInfo.getCertificationRule().getWeeklyCount())
                        .weeklyDays(goalInfo.getCertificationRule().getWeeklyDays())
                        .monthlyDays(goalInfo.getCertificationRule().getMonthlyDays().stream()
                                .map(String::valueOf)
                                .collect(java.util.stream.Collectors.toList()))
                        .goalDate(goalInfo.getCertificationRule().getGoalDate())
                        .build())
                .build();

        // 목표는 독립적으로 관리
        createGoal(groupId, goalRequest, creatorId);
    }

    /**
     * 인증 규칙을 생성합니다.
     */
    private Integer createCertificationRule(String groupId,
            GroupCreateRequestDto.CertificationInfo ruleInfo) {

        log.info("Creating certification rule for group: {} with frequency: {}",
                groupId, ruleInfo.getCertificationFrequency());

        Timestamp now = new Timestamp(System.currentTimeMillis());

        CertificationRuleEntity certificationRule = new CertificationRuleEntity();
        certificationRule.setGroupId(groupId);
        certificationRule.setContent(ruleInfo.getContent());
        certificationRule.setImageRequired(ruleInfo.getImageRequired() ? (byte) 1 : (byte) 0);
        certificationRule.setTextRequired(ruleInfo.getTextRequired() ? (byte) 1 : (byte) 0);
        certificationRule.setCertificationFrequency(ruleInfo.getCertificationFrequency());
        certificationRule.setWeeklyCount(ruleInfo.getWeeklyCount());
        certificationRule.setGoalDate(
                ruleInfo.getGoalDate() != null ? Timestamp.valueOf(ruleInfo.getGoalDate().atStartOfDay()) : null);
        certificationRule.setCreatedAt(now);
        certificationRule.setUpdatedAt(now);

        // 주간/월간 인증 설정
        if (ruleInfo.getWeeklyDays() != null) {
            certificationRule.setWeeklyDays(String.join(",", ruleInfo.getWeeklyDays()));
        }
        if (ruleInfo.getMonthlyDays() != null) {
            certificationRule.setMonthlyDays(ruleInfo.getMonthlyDays().stream()
                    .map(String::valueOf)
                    .collect(java.util.stream.Collectors.joining(",")));
        }

        // 유효성 검증
        validateCertificationRule(certificationRule);

        CertificationRuleEntity saved = certificationRuleRepository.save(certificationRule);
        log.info("Certification rule created with ID: {}", saved.getCertificationRuleId());

        return saved.getCertificationRuleId();
    }

    /**
     * 인증 규칙 유효성 검증
     */
    private void validateCertificationRule(CertificationRuleEntity certificationRule) {
        // 주간 인증의 경우 weeklyCount와 weeklyDays 개수가 일치해야 함
        if ("WEEKLY".equals(certificationRule.getCertificationFrequency())) {
            if (certificationRule.getWeeklyCount() == null || certificationRule.getWeeklyDays() == null) {
                throw new TribeApiException(ErrorCode.CERTIFICATION_RULE_INVALID_WEEKLY);
            }

            String[] days = certificationRule.getWeeklyDays().split(",");
            if (!certificationRule.getWeeklyCount().equals(days.length)) {
                throw new TribeApiException(ErrorCode.CERTIFICATION_RULE_INVALID_WEEKLY);
            }
        }

        // 월간 인증의 경우 monthlyDays가 있어야 함
        if ("MONTHLY".equals(certificationRule.getCertificationFrequency())) {
            String monthlyDays = certificationRule.getMonthlyDays();
            if (monthlyDays == null || monthlyDays.trim().isEmpty()) {
                throw new TribeApiException(ErrorCode.CERTIFICATION_RULE_INVALID_MONTHLY);
            }

            String[] days = monthlyDays.split(",");
            for (String day : days) {
                try {
                    int dayInt = Integer.parseInt(day.trim());
                    if (dayInt < 1 || dayInt > 31) {
                        throw new TribeApiException(ErrorCode.CERTIFICATION_RULE_INVALID_MONTHLY);
                    }
                } catch (NumberFormatException e) {
                    throw new TribeApiException(ErrorCode.CERTIFICATION_RULE_INVALID_MONTHLY);
                }
            }
        }

        // 텍스트나 이미지 중 최소 하나는 필수
        if (certificationRule.getTextRequired() == 0 && certificationRule.getImageRequired() == 0) {
            throw new TribeApiException(ErrorCode.CERTIFICATION_RULE_NO_METHOD);
        }
    }

    /**
     * GroupEntity를 GroupDetailResponseDto로 변환
     */
    private GroupDetailResponseDto convertToDetailResponseDto(GroupEntity entity) {
        return GroupDetailResponseDto.builder()
                .id(entity.getGroupId())
                .name(entity.getTitle())
                .description(entity.getDescription())
                .imageUrl(entity.getThumbnail())
                .categoryCode(entity.getCategoryCode())
                .categoryName(getCategoryName(entity.getCategoryCode()))
                .currentMembers(1) // 호스트 포함, 실제로는 멤버 수 조회 필요
                .maxMembers(entity.getParticipants())
                .status(entity.getStatus().name())
                .leaderId(entity.getHostId())
                .leaderName("닉네임") // 실제로는 User 정보에서 조회
                .members(null) // 실제로는 멤버 목록 조회 필요
                .goals(null) // 실제로는 목표 목록 조회 필요
                .createdAt(entity.getCreatedAt().toLocalDateTime())
                .updatedAt(entity.getUpdatedAt().toLocalDateTime())
                .build();
    }

    /**
     * 카테고리 코드로 카테고리 이름 조회
     */
    private String getCategoryName(String categoryCode) {
        try {
            GroupCategory.GroupCategoryCode code = GroupCategory.GroupCategoryCode.findByCode(categoryCode);
            return code.getDescription();
        } catch (IllegalArgumentException e) {
            log.warn("Unknown category code: {}", categoryCode);
            return categoryCode;
        }
    }

    // QueryDSL 활용 메서드들 구현
    @Override
    public CommonPageDto<List<GroupListResponseDto>> getGroupsWithFilter(Pageable pageable,
            GroupFilterRequestDto filterRequest) {
        log.info("Getting groups with filter: {}", filterRequest);

        Page<GroupEntity> groupPage;

        if (filterRequest == null || filterRequest.isEmpty()) {
            // 필터가 없으면 기본 조회 (삭제되지 않은 그룹만)
            groupPage = groupRepository.findAllByStatus(GroupEntity.GroupStatus.WAITING, pageable);
        } else {
            // QueryDSL을 사용한 동적 검색
            groupPage = groupRepository.findGroupsWithFilter(filterRequest, pageable);
        }

        List<GroupListResponseDto> groupList = groupPage.getContent().stream()
                .map(this::convertToGroupListResponseDto)
                .toList();

        return CommonPageDto.of(
                groupPage.getNumber(),
                groupPage.getSize(),
                (int) groupPage.getTotalElements(),
                groupPage.getTotalPages(),
                groupList);
    }

    @Override
    public GroupDetailResponseDto getGroup(String groupId) {
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        return convertToDetailResponseDto(group);
    }

    @Override
    public CommonPageDto<List<GroupListResponseDto>> getGroupsByCategory(String categoryCode,
            Pageable pageable, String status) {
        log.info("Getting groups by category: {} with status: {}", categoryCode, status);

        Page<GroupEntity> groupPage = groupRepository.findGroupsByCategoryAndStatus(categoryCode, status, pageable);

        List<GroupListResponseDto> groupList = groupPage.getContent().stream()
                .map(this::convertToGroupListResponseDto)
                .toList();

        return CommonPageDto.of(
                groupPage.getNumber(),
                groupPage.getSize(),
                (int) groupPage.getTotalElements(),
                groupPage.getTotalPages(),
                groupList);
    }

    /**
     * 키워드 검색 (제목, 설명에서 검색)
     */
    public CommonPageDto<List<GroupListResponseDto>> searchGroupsByKeyword(String keyword, Pageable pageable) {
        log.info("Searching groups by keyword: {}", keyword);

        Page<GroupEntity> groupPage = groupRepository.findGroupsByKeyword(keyword, pageable);

        List<GroupListResponseDto> groupList = groupPage.getContent().stream()
                .map(this::convertToGroupListResponseDto)
                .toList();

        return CommonPageDto.of(
                groupPage.getNumber(),
                groupPage.getSize(),
                (int) groupPage.getTotalElements(),
                groupPage.getTotalPages(),
                groupList);
    }

    /**
     * 지역별 그룹 검색
     */
    public CommonPageDto<List<GroupListResponseDto>> searchGroupsByRegions(List<String> regions, Pageable pageable) {
        log.info("Searching groups by regions: {}", regions);

        Page<GroupEntity> groupPage = groupRepository.findGroupsByRegions(regions, pageable);

        List<GroupListResponseDto> groupList = groupPage.getContent().stream()
                .map(this::convertToGroupListResponseDto)
                .toList();

        return CommonPageDto.of(
                groupPage.getNumber(),
                groupPage.getSize(),
                (int) groupPage.getTotalElements(),
                groupPage.getTotalPages(),
                groupList);
    }

    /**
     * GroupEntity를 GroupListResponseDto로 변환
     */
    private GroupListResponseDto convertToGroupListResponseDto(GroupEntity group) {
        // TODO: 실제 멤버 수 계산 로직 추가
        int currentMemberCount = 1; // 임시값 (호스트 포함)

        return GroupListResponseDto.builder()
                .id(group.getGroupId())
                .name(group.getTitle())
                .description(group.getDescription())
                .imageUrl(group.getThumbnail())
                .categoryCode(group.getCategoryCode())
                .categoryName(getCategoryName(group.getCategoryCode()))
                .currentMembers(currentMemberCount)
                .maxMembers(group.getParticipants())
                .status(group.getStatus().name())
                .createdAt(group.getCreatedAt().toLocalDateTime())
                .updatedAt(group.getUpdatedAt().toLocalDateTime())
                .build();
    }

    @Override
    @Transactional
    public GroupDetailResponseDto updateGroup(String groupId, GroupUpdateRequestDto request, String userId) {
        log.info("Updating group: {} by user: {}", groupId, userId);

        // 1. 그룹 존재 확인
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 2. 권한 확인 (호스트만 수정 가능)
        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.MEMBER_NOT_HOST);
        }

        // 3. 그룹 정보 업데이트
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (request.getName() != null) {
            group.setTitle(request.getName());
        }
        if (request.getDescription() != null) {
            group.setDescription(request.getDescription());
        }
        if (request.getImageUrl() != null) {
            group.setThumbnail(request.getImageUrl());
        }
        if (request.getMaxMembers() != null) {
            group.setParticipants(request.getMaxMembers());
        }
        if (request.getCategoryCode() != null) {
            group.setCategoryCode(request.getCategoryCode().toString());
        }

        group.setUpdatedAt(now);

        GroupEntity updatedGroup = groupRepository.save(group);
        log.info("Group updated successfully: {}", updatedGroup.getGroupId());

        return convertToDetailResponseDto(updatedGroup);
    }

    @Override
    @Transactional
    public void deleteGroup(String groupId, String userId) {
        log.info("Deleting group: {} by user: {}", groupId, userId);

        // 1. 그룹 존재 확인
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 2. 권한 확인 (호스트만 삭제 가능)
        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.MEMBER_NOT_HOST);
        }

        // 3. 그룹 상태를 DISBANDED로 변경 (soft delete)
        group.setStatus(GroupEntity.GroupStatus.DISBANDED);
        group.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        groupRepository.save(group);
        log.info("Group deleted successfully: {}", groupId);
    }

    @Override
    @Transactional
    public void joinGroup(String groupId, String userId) {
        log.info("User: {} joining group: {}", userId, groupId);

        // 1. 그룹 존재 확인
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 2. 그룹 상태 확인 (WAITING 상태만 참여 가능)
        if (group.getStatus() != GroupEntity.GroupStatus.WAITING) {
            throw new TribeApiException(ErrorCode.INVALID_GROUP_STATUS);
        }

        // 3. 중복 참여 확인
        if (memberRepository.findByGroupIdAndUserId(groupId, userId).isPresent()) {
            throw new TribeApiException(ErrorCode.GROUP_ALREADY_JOINED);
        }

        // 4. 정원 확인
        List<MemberEntity> currentMembers = memberRepository.findAllByGroupId(groupId);
        if (currentMembers.size() >= group.getParticipants()) {
            throw new TribeApiException(ErrorCode.GROUP_FULL);
        }

        // 5. 멤버 추가
        MemberEntity member = new MemberEntity();
        member.setUserId(userId);
        member.setGroupId(groupId);
        member.setRole("MEMBER");
        member.setStatus("ACTIVE");
        member.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        memberRepository.save(member);
        log.info("User: {} joined group: {} successfully", userId, groupId);
    }

    @Override
    @Transactional
    public void leaveGroup(String groupId, String userId) {
        log.info("User: {} leaving group: {}", userId, groupId);

        // 1. 그룹 존재 확인
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 2. 호스트는 탈퇴 불가
        if (group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.GROUP_HOST_CANNOT_LEAVE);
        }

        // 3. 멤버 확인
        MemberEntity member = memberRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_JOINED));

        // 4. 멤버 상태를 INACTIVE로 변경하고 삭제 시간 설정
        member.setStatus("INACTIVE");
        member.setDeletedAt(new Timestamp(System.currentTimeMillis()));

        memberRepository.save(member);
        log.info("User: {} left group: {} successfully", userId, groupId);
    }

    // Goal 관련 메서드들 - 기존 구현 활용
    @Override
    @Transactional
    public GoalResponseDto createGoal(String groupId, GoalCreateRequestDto request, String userId) {
        log.info("Creating goal for group: {} by user: {}", groupId, userId);

        // 1. 그룹 존재 및 권한 확인
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.MEMBER_NOT_HOST);
        }

        // 2. 목표 생성
        Timestamp now = new Timestamp(System.currentTimeMillis());

        GoalEntity goalEntity = GoalEntity.builder()
                .groupId(groupId)
                .title(request.getTitle())
                .description(request.getDescription())
                .targetValue(request.getTargetValue())
                .status(GoalEntity.GoalStatus.ACTIVE)
                .startDate(Timestamp.valueOf(request.getStartDate().atStartOfDay()))
                .endDate(Timestamp.valueOf(request.getEndDate().atTime(23, 59, 59)))
                .createdAt(now)
                .updatedAt(now)
                .build();

        // 3. 인증 규칙이 있는 경우 생성 후 연결
        if (request.getCertificationRule() != null) {
            Integer certificationRuleId = createCertificationRuleForGoal(groupId, request.getCertificationRule());
            goalEntity.setCertificationRuleId(certificationRuleId);
        }

        GoalEntity savedGoal = goalRepository.save(goalEntity);
        log.info("Goal created with ID: {}", savedGoal.getGoalId());

        return convertToGoalResponseDto(savedGoal);
    }

    /**
     * 목표를 위한 인증 규칙 생성
     */
    private Integer createCertificationRuleForGoal(String groupId, CertificationRuleRequestDto ruleRequest) {
        log.info("Creating certification rule for group: {}", groupId);

        Timestamp now = new Timestamp(System.currentTimeMillis());

        CertificationRuleEntity certificationRule = new CertificationRuleEntity();
        certificationRule.setGroupId(groupId);
        certificationRule.setContent(ruleRequest.getContent());
        certificationRule.setImageRequired(ruleRequest.isImageRequired() ? (byte) 1 : (byte) 0);
        certificationRule.setTextRequired(ruleRequest.isTextRequired() ? (byte) 1 : (byte) 0);
        certificationRule.setCertificationFrequency(ruleRequest.getCertificationFrequency());
        certificationRule.setWeeklyCount(ruleRequest.getWeeklyCount());
        certificationRule.setGoalDate(
                ruleRequest.getGoalDate() != null ? Timestamp.valueOf(ruleRequest.getGoalDate().atStartOfDay())
                        : null);
        certificationRule.setCreatedAt(now);
        certificationRule.setUpdatedAt(now);

        // 주간/월간 인증 설정
        if (ruleRequest.getWeeklyDays() != null) {
            certificationRule.setWeeklyDays(String.join(",", ruleRequest.getWeeklyDays()));
        }
        if (ruleRequest.getMonthlyDays() != null) {
            certificationRule.setMonthlyDays(String.join(",", ruleRequest.getMonthlyDays()));
        }

        // 유효성 검증
        validateCertificationRule(certificationRule);

        CertificationRuleEntity saved = certificationRuleRepository.save(certificationRule);
        log.info("Certification rule created with ID: {}", saved.getCertificationRuleId());

        return saved.getCertificationRuleId();
    }

    /**
     * GoalEntity를 GoalResponseDto로 변환
     */
    private GoalResponseDto convertToGoalResponseDto(GoalEntity entity) {
        return GoalResponseDto.builder()
                .id(entity.getGoalId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate().toLocalDateTime().toLocalDate().toString())
                .endDate(entity.getEndDate() != null ? entity.getEndDate().toLocalDateTime().toLocalDate().toString()
                        : null)
                .status(entity.getStatus().name())
                .createdBy("사용자") // 실제로는 User 정보 조회 필요
                .createdAt(entity.getCreatedAt().toLocalDateTime())
                .updatedAt(entity.getUpdatedAt().toLocalDateTime())
                .build();
    }

    @Override
    public List<GoalResponseDto> getGoals(String groupId) {
        // 그룹 존재 확인
        if (!groupRepository.existsById(groupId)) {
            throw new TribeApiException(ErrorCode.GROUP_NOT_FOUND);
        }

        List<GoalEntity> goals = goalRepository.findAllByGroupId(groupId);
        return goals.stream()
                .map(this::convertToGoalResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public GoalResponseDto updateGoal(String groupId, Long goalId, GoalUpdateRequestDto request, String userId) {
        log.info("Updating goal: {} for group: {} by user: {}", goalId, groupId, userId);

        // 1. 그룹 존재 및 권한 확인
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.MEMBER_NOT_HOST);
        }

        // 2. 목표 존재 확인
        GoalEntity goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GOAL_NOT_FOUND));

        // 3. 목표가 해당 그룹에 속하는지 확인
        if (!goal.getGroupId().equals(groupId)) {
            throw new TribeApiException(ErrorCode.GOAL_NOT_FOUND);
        }

        // 4. 목표 정보 업데이트
        Timestamp now = new Timestamp(System.currentTimeMillis());

        if (request.getTitle() != null) {
            goal.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            goal.setDescription(request.getDescription());
        }
        if (request.getStartDate() != null) {
            goal.setStartDate(Timestamp.valueOf(java.time.LocalDate.parse(request.getStartDate()).atStartOfDay()));
        }
        if (request.getEndDate() != null) {
            goal.setEndDate(Timestamp.valueOf(java.time.LocalDate.parse(request.getEndDate()).atTime(23, 59, 59)));
        }

        goal.setUpdatedAt(now);

        GoalEntity updatedGoal = goalRepository.save(goal);
        log.info("Goal updated successfully: {}", updatedGoal.getGoalId());

        return convertToGoalResponseDto(updatedGoal);
    }

    @Override
    @Transactional
    public void deleteGoal(String groupId, Long goalId, String userId) {
        log.info("Deleting goal: {} for group: {} by user: {}", goalId, groupId, userId);

        // 1. 그룹 존재 및 권한 확인
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.MEMBER_NOT_HOST);
        }

        // 2. 목표 존재 확인
        GoalEntity goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GOAL_NOT_FOUND));

        // 3. 목표가 해당 그룹에 속하는지 확인
        if (!goal.getGroupId().equals(groupId)) {
            throw new TribeApiException(ErrorCode.GOAL_NOT_FOUND);
        }

        // 4. 목표 삭제 (상태를 FAILED로 변경)
        goal.setStatus(GoalEntity.GoalStatus.FAILED);
        goal.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        goalRepository.save(goal);
        log.info("Goal deleted successfully: {}", goalId);
    }
}