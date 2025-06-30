package com.unity.tribe.domain.group.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeApiException;
import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.group.dto.request.*;
import com.unity.tribe.domain.group.dto.response.GoalResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupDetailResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupListResponseDto;
import com.unity.tribe.domain.group.entity.GoalEntity;
import com.unity.tribe.domain.group.entity.GroupCategory;
import com.unity.tribe.domain.group.entity.GroupEntity;
import com.unity.tribe.domain.group.repository.GoalRepository;
import com.unity.tribe.domain.group.repository.GroupRepository;
import com.unity.tribe.domain.group.repository.MemberRepository;
import com.unity.tribe.domain.member.dto.response.MemberResponseDto;
import com.unity.tribe.domain.member.entity.MemberEntity;
import com.unity.tribe.domain.user.entity.UserEntity;
import com.unity.tribe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<GroupListResponseDto>> getGroups(Pageable pageable, String status) {
        GroupEntity.GroupStatus groupStatus = null;
        if (status != null) {
            try {
                groupStatus = GroupEntity.GroupStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new TribeApiException(ErrorCode.INVALID_GROUP_STATUS);
            }
        }

        Page<GroupEntity> groupPage;
        if (groupStatus != null) {
            groupPage = groupRepository.findAllByStatus(groupStatus, pageable);
        } else {
            groupPage = groupRepository.findAll(pageable);
        }

        List<GroupListResponseDto> groupList = groupPage.getContent().stream()
                .map(this::convertToGroupListResponseDto)
                .collect(Collectors.toList());

        return CommonPageDto.of(
                groupPage.getNumber(),
                groupPage.getSize(),
                (int) groupPage.getTotalElements(),
                groupPage.getTotalPages(),
                groupList);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<GroupListResponseDto>> getGroupsByCategory(String categoryCode, Pageable pageable,
            String status) {
        GroupEntity.GroupStatus groupStatus = null;
        if (status != null) {
            try {
                groupStatus = GroupEntity.GroupStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new TribeApiException(ErrorCode.INVALID_GROUP_STATUS);
            }
        }

        Page<GroupEntity> groupPage = groupRepository.findAllByCategoryCodeAndStatus(categoryCode, groupStatus,
                pageable);

        List<GroupListResponseDto> groupList = groupPage.getContent().stream()
                .map(this::convertToGroupListResponseDto)
                .collect(Collectors.toList());

        return CommonPageDto.of(
                groupPage.getNumber(),
                groupPage.getSize(),
                (int) groupPage.getTotalElements(),
                groupPage.getTotalPages(),
                groupList);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<GroupListResponseDto>> searchGroups(String keyword, Pageable pageable) {
        Page<GroupEntity> groupPage = groupRepository.searchByKeyword(keyword, pageable);

        List<GroupListResponseDto> groupList = groupPage.getContent().stream()
                .map(this::convertToGroupListResponseDto)
                .collect(Collectors.toList());

        return CommonPageDto.of(
                groupPage.getNumber(),
                groupPage.getSize(),
                (int) groupPage.getTotalElements(),
                groupPage.getTotalPages(),
                groupList);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupDetailResponseDto getGroup(String groupId) {
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        return convertToGroupDetailResponseDto(group);
    }

    @Override
    public GroupDetailResponseDto createGroup(GroupCreateRequestDto request, String userId) {
        // 사용자 검증
        if (!userRepository.existsById(userId)) {
            throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
        }

        // 그룹 ID 생성
        String groupId = UUID.randomUUID().toString().replace("-", "").substring(0, 26).toUpperCase();

        // 그룹 엔티티 생성
        GroupEntity group = GroupEntity.builder()
                .groupId(groupId)
                .hostId(userId)
                .categoryCode(request.getCategoryCode().getCode())
                .title(request.getTitle())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .participants(request.getParticipants())
                .groupType(request.getGroupType())
                .meetingType(request.getMeetingType())
                .locationAddress(request.getLocationAddress())
                .genderRestriction(request.getGenderRestriction())
                .minAge(request.getMinAge())
                .maxAge(request.getMaxAge())
                .status(GroupEntity.GroupStatus.WAITING)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .expiredAt(request.getExpiredAt() != null ? Timestamp.valueOf(request.getExpiredAt()) : null)
                .build();

        GroupEntity savedGroup = groupRepository.save(group);

        // 호스트를 멤버로 추가
        MemberEntity hostMember = new MemberEntity();
        hostMember.setUserId(userId);
        hostMember.setGroupId(groupId);
        hostMember.setRole("HOST");
        hostMember.setStatus("ACTIVE");
        hostMember.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        memberRepository.save(hostMember);

        return convertToGroupDetailResponseDto(savedGroup);
    }

    @Override
    public GroupDetailResponseDto updateGroup(String groupId, GroupUpdateRequestDto request, String userId) {
        // 그룹 조회
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 호스트 권한 확인
        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.GROUP_ACCESS_DENIED);
        }

        // 그룹 정보 업데이트
        if (request.getName() != null) {
            group.setTitle(request.getName());
        }
        if (request.getDescription() != null) {
            group.setDescription(request.getDescription());
        }
        if (request.getImageUrl() != null) {
            group.setThumbnail(request.getImageUrl());
        }
        group.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        GroupEntity updatedGroup = groupRepository.save(group);
        return convertToGroupDetailResponseDto(updatedGroup);
    }

    @Override
    public void deleteGroup(String groupId, String userId) {
        // 그룹 조회
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 호스트 권한 확인
        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.GROUP_ACCESS_DENIED);
        }

        // 그룹 상태를 DISBANDED로 변경
        group.setStatus(GroupEntity.GroupStatus.DISBANDED);
        group.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        groupRepository.save(group);

        // TODO: 그룹에 속한 사용자들의 피드 및 댓글 삭제
    }

    @Override
    public void joinGroup(String groupId, String userId) {
        // 그룹 조회
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 사용자 검증
        if (!userRepository.existsById(userId)) {
            throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
        }

        // 이미 가입된 멤버인지 확인
        Optional<MemberEntity> existingMember = memberRepository.findByGroupIdAndUserId(groupId, userId);
        if (existingMember.isPresent() && existingMember.get().getDeletedAt() == null) {
            throw new TribeApiException(ErrorCode.MEMBER_ALREADY_JOINED);
        }

        // 그룹 정원 확인
        List<MemberEntity> activeMembers = memberRepository.findAllByGroupId(groupId).stream()
                .filter(member -> member.getDeletedAt() == null)
                .collect(Collectors.toList());

        if (activeMembers.size() >= group.getParticipants()) {
            throw new TribeApiException(ErrorCode.GROUP_FULL);
        }

        // 멤버 추가
        MemberEntity member = new MemberEntity();
        member.setUserId(userId);
        member.setGroupId(groupId);
        member.setRole("MEMBER");
        member.setStatus("ACTIVE");
        member.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        memberRepository.save(member);
    }

    @Override
    public void leaveGroup(String groupId, String userId) {
        // 그룹 조회
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 멤버 조회
        MemberEntity member = memberRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.MEMBER_NOT_FOUND));

        // 호스트는 탈퇴할 수 없음 (그룹을 삭제해야 함)
        if ("HOST".equals(member.getRole())) {
            throw new TribeApiException(ErrorCode.MEMBER_CANNOT_KICK_HOST);
        }

        // 소프트 삭제 처리
        member.setDeletedAt(Timestamp.valueOf(LocalDateTime.now()));
        member.setStatus("EXITED");
        memberRepository.save(member);
    }

    @Override
    public GoalResponseDto createGoal(String groupId, GoalCreateRequestDto request, String userId) {
        // 그룹 조회
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 호스트 권한 확인
        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.GROUP_ACCESS_DENIED);
        }

        // 목표 엔티티 생성
        GoalEntity goal = GoalEntity.builder()
                .groupId(groupId)
                .title(request.getTitle())
                .description(request.getDescription())
                .targetValue(request.getTargetValue())
                .status(GoalEntity.GoalStatus.ACTIVE)
                .startDate(Timestamp.valueOf(request.getStartDate().atStartOfDay()))
                .endDate(request.getEndDate() != null ? Timestamp.valueOf(request.getEndDate().atStartOfDay()) : null)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        GoalEntity savedGoal = goalRepository.save(goal);
        return convertToGoalResponseDto(savedGoal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoalResponseDto> getGoals(String groupId) {
        // 그룹 존재 확인
        if (!groupRepository.existsById(groupId)) {
            throw new TribeApiException(ErrorCode.GROUP_NOT_FOUND);
        }

        List<GoalEntity> goals = goalRepository.findAllByGroupId(groupId);
        return goals.stream()
                .map(this::convertToGoalResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public GoalResponseDto updateGoal(String groupId, Long goalId, GoalUpdateRequestDto request, String userId) {
        // 그룹 조회
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 호스트 권한 확인
        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.GROUP_ACCESS_DENIED);
        }

        // 목표 조회
        GoalEntity goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.RESOURCE_NOT_FOUND));

        // 목표 정보 업데이트
        if (request.getTitle() != null) {
            goal.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            goal.setDescription(request.getDescription());
        }
        if (request.getStartDate() != null) {
            goal.setStartDate(Timestamp.valueOf(LocalDate.parse(request.getStartDate()).atStartOfDay()));
        }
        if (request.getEndDate() != null) {
            goal.setEndDate(Timestamp.valueOf(LocalDate.parse(request.getEndDate()).atStartOfDay()));
        }
        goal.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        GoalEntity updatedGoal = goalRepository.save(goal);
        return convertToGoalResponseDto(updatedGoal);
    }

    @Override
    public void deleteGoal(String groupId, Long goalId, String userId) {
        // 그룹 조회
        GroupEntity group = groupRepository.findById(groupId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.GROUP_NOT_FOUND));

        // 호스트 권한 확인
        if (!group.getHostId().equals(userId)) {
            throw new TribeApiException(ErrorCode.GROUP_ACCESS_DENIED);
        }

        // 목표 조회
        GoalEntity goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.RESOURCE_NOT_FOUND));

        goalRepository.delete(goal);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<GroupListResponseDto>> getGroupsWithFilter(Pageable pageable,
            GroupFilterRequestDto filter) {
        // 필터링 구현은 복잡하므로 기본 구현으로 대체
        return getGroups(pageable, filter != null ? filter.getStatus() : null);
    }

    // 변환 메서드들
    private GroupListResponseDto convertToGroupListResponseDto(GroupEntity group) {
        List<MemberEntity> activeMembers = memberRepository.findAllByGroupId(group.getGroupId()).stream()
                .filter(member -> member.getDeletedAt() == null)
                .collect(Collectors.toList());

        return GroupListResponseDto.builder()
                .id(group.getGroupId())
                .name(group.getTitle())
                .description(group.getDescription())
                .imageUrl(group.getThumbnail())
                .categoryCode(group.getCategoryCode())
                .categoryName(getCategoryName(group.getCategoryCode()))
                .currentMembers(activeMembers.size())
                .maxMembers(group.getParticipants())
                .status(group.getStatus().name())
                .createdAt(group.getCreatedAt().toLocalDateTime())
                .updatedAt(group.getUpdatedAt().toLocalDateTime())
                .build();
    }

    private GroupDetailResponseDto convertToGroupDetailResponseDto(GroupEntity group) {
        List<MemberEntity> activeMembers = memberRepository.findAllByGroupId(group.getGroupId()).stream()
                .filter(member -> member.getDeletedAt() == null)
                .collect(Collectors.toList());

        List<GoalEntity> goals = goalRepository.findAllByGroupId(group.getGroupId());
        Optional<UserEntity> host = userRepository.findById(group.getHostId());

        // 멤버 목록 생성
        List<MemberResponseDto> memberList = activeMembers.stream()
                .map(member -> {
                    UserEntity user = userRepository.findById(member.getUserId()).orElse(null);
                    return MemberResponseDto.builder()
                            .userId(member.getUserId())
                            .nickname(user != null ? user.getNickname() : "탈퇴한 사용자")
                            .profileImg(user != null ? user.getProfileImg() : null)
                            .role(member.getRole())
                            .status(member.getStatus() != null ? member.getStatus() : "ACTIVE")
                            .joinedAt(member.getCreatedAt().toLocalDateTime())
                            .build();
                })
                .collect(Collectors.toList());

        return GroupDetailResponseDto.builder()
                .id(group.getGroupId())
                .name(group.getTitle())
                .description(group.getDescription())
                .imageUrl(group.getThumbnail())
                .categoryCode(group.getCategoryCode())
                .categoryName(getCategoryName(group.getCategoryCode()))
                .currentMembers(activeMembers.size())
                .maxMembers(group.getParticipants())
                .status(group.getStatus().name())
                .leaderId(group.getHostId())
                .leaderName(host.map(UserEntity::getNickname).orElse("탈퇴한 사용자"))
                .members(memberList)
                .goals(goals.stream().map(this::convertToGoalResponseDto).collect(Collectors.toList()))
                .createdAt(group.getCreatedAt().toLocalDateTime())
                .updatedAt(group.getUpdatedAt().toLocalDateTime())
                .build();
    }

    private GoalResponseDto convertToGoalResponseDto(GoalEntity goal) {
        return GoalResponseDto.builder()
                .id(goal.getGoalId())
                .title(goal.getTitle())
                .description(goal.getDescription())
                .startDate(goal.getStartDate().toString())
                .endDate(goal.getEndDate() != null ? goal.getEndDate().toString() : null)
                .status(goal.getStatus().name())
                .createdBy("시스템") // 생성자 정보 구현 필요
                .createdAt(goal.getCreatedAt().toLocalDateTime())
                .updatedAt(goal.getUpdatedAt().toLocalDateTime())
                .build();
    }

    private String getCategoryName(String categoryCode) {
        try {
            GroupCategory.GroupCategoryCode code = GroupCategory.GroupCategoryCode.findByCode(categoryCode);
            return code.getDescription();
        } catch (IllegalArgumentException e) {
            return "알 수 없는 카테고리";
        }
    }
}