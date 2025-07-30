package com.unity.tribe.domain.group.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.group.dto.request.GoalCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GoalUpdateRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupFilterRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupUpdateRequestDto;
import com.unity.tribe.domain.group.dto.response.GoalResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupDetailResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupListResponseDto;

/**
 * 그룹 관련 서비스 인터페이스
 */
public interface GroupService {

    // Group 관련 메서드들
    /**
     * 새로운 모임을 생성합니다.
     * 목표와 인증 규칙도 함께 생성할 수 있습니다.
     * 
     * @param request 그룹 생성 요청 정보
     * @param hostId  호스트 사용자 ID
     * @return 생성된 그룹 상세 정보
     */
    GroupDetailResponseDto createGroup(GroupCreateRequestDto request, String hostId);

    /**
     * 필터 조건에 따른 모임 목록을 조회합니다.
     * 
     * @param pageable      페이지 정보
     * @param filterRequest 필터링 조건
     * @return 그룹 목록 페이지 정보
     */
    CommonPageDto<List<GroupListResponseDto>> getGroupsWithFilter(Pageable pageable,
            GroupFilterRequestDto filterRequest);

    /**
     * 특정 모임의 상세 정보를 조회합니다.
     * 
     * @param groupId 그룹 ID
     * @return 그룹 상세 정보
     */
    GroupDetailResponseDto getGroup(String groupId);

    /**
     * 카테고리별 모임 목록을 조회합니다.
     * 
     * @param categoryCode 카테고리 코드
     * @param pageable     페이지 정보
     * @param status       그룹 상태
     * @return 그룹 목록 페이지 정보
     */
    CommonPageDto<List<GroupListResponseDto>> getGroupsByCategory(String categoryCode, Pageable pageable,
            String status);

    /**
     * 모임 정보를 수정합니다.
     * 
     * @param groupId 그룹 ID
     * @param request 그룹 수정 요청 정보
     * @param userId  사용자 ID
     * @return 수정된 그룹 상세 정보
     */
    GroupDetailResponseDto updateGroup(String groupId, GroupUpdateRequestDto request, String userId);

    /**
     * 모임을 삭제합니다.
     * 
     * @param groupId 그룹 ID
     * @param userId  사용자 ID
     */
    void deleteGroup(String groupId, String userId);

    /**
     * 모임에 참여합니다.
     * 
     * @param groupId 그룹 ID
     * @param userId  사용자 ID
     */
    void joinGroup(String groupId, String userId);

    /**
     * 모임에서 탈퇴합니다.
     * 
     * @param groupId 그룹 ID
     * @param userId  사용자 ID
     */
    void leaveGroup(String groupId, String userId);

    // Goal 관련 메서드들
    /**
     * 모임에 새로운 목표를 생성합니다.
     * 
     * @param groupId 그룹 ID
     * @param request 목표 생성 요청 정보
     * @param userId  사용자 ID
     * @return 생성된 목표 정보
     */
    GoalResponseDto createGoal(String groupId, GoalCreateRequestDto request, String userId);

    /**
     * 모임의 모든 목표를 조회합니다.
     * 
     * @param groupId 그룹 ID
     * @return 목표 목록
     */
    List<GoalResponseDto> getGoals(String groupId);

    /**
     * 모임의 특정 목표를 수정합니다.
     * 
     * @param groupId 그룹 ID
     * @param goalId  목표 ID
     * @param request 목표 수정 요청 정보
     * @param userId  사용자 ID
     * @return 수정된 목표 정보
     */
    GoalResponseDto updateGoal(String groupId, Long goalId, GoalUpdateRequestDto request, String userId);

    /**
     * 모임의 특정 목표를 삭제합니다.
     * 
     * @param groupId 그룹 ID
     * @param goalId  목표 ID
     * @param userId  사용자 ID
     */
    void deleteGoal(String groupId, Long goalId, String userId);
}