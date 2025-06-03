package com.unity.tribe.domain.group.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.group.dto.request.*;
import com.unity.tribe.domain.group.dto.response.GoalResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupDetailResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupListResponseDto;

/**
 * 그룹 관련 서비스 인터페이스
 */
public interface GroupService {

    /**
     * 필터링 조건을 적용한 그룹 목록을 조회합니다.
     * 
     * @param pageable 페이지 정보
     * @param filter   필터링 조건
     * @return 그룹 목록 페이지 정보
     */
    CommonPageDto<List<GroupListResponseDto>> getGroupsWithFilter(Pageable pageable, GroupFilterRequestDto filter);

    /**
     * 그룹 목록을 조회합니다. (기존 호환성 유지)
     * 
     * @param pageable 페이지 정보
     * @param status   그룹 상태
     * @return 그룹 목록 페이지 정보
     */
    CommonPageDto<List<GroupListResponseDto>> getGroups(Pageable pageable, String status);

    /**
     * 카테고리별 그룹 목록을 조회합니다. (기존 호환성 유지)
     * 
     * @param categoryId 카테고리 ID
     * @param pageable   페이지 정보
     * @param status     그룹 상태
     * @return 그룹 목록 페이지 정보
     */
    CommonPageDto<List<GroupListResponseDto>> getGroupsByCategory(Long categoryId, Pageable pageable, String status);

    /**
     * 키워드로 그룹을 검색합니다. (기존 호환성 유지)
     * 
     * @param keyword  검색 키워드
     * @param pageable 페이지 정보
     * @return 검색된 그룹 목록 페이지 정보
     */
    CommonPageDto<List<GroupListResponseDto>> searchGroups(String keyword, Pageable pageable);

    /**
     * 그룹 상세 정보를 조회합니다.
     * 
     * @param groupId 그룹 ID
     * @return 그룹 상세 정보
     */
    GroupDetailResponseDto getGroup(String groupId);

    /**
     * 새로운 그룹을 생성합니다.
     * 
     * @param request  그룹 생성 요청 정보
     * @param username 사용자 이름
     * @return 생성된 그룹 상세 정보
     */
    GroupDetailResponseDto createGroup(GroupCreateRequestDto request, String username);

    /**
     * 그룹 정보를 수정합니다.
     * 
     * @param groupId  그룹 ID
     * @param request  그룹 수정 요청 정보
     * @param username 사용자 이름
     * @return 수정된 그룹 상세 정보
     */
    GroupDetailResponseDto updateGroup(String groupId, GroupUpdateRequestDto request, String username);

    /**
     * 그룹을 삭제합니다.
     * 
     * @param groupId  그룹 ID
     * @param username 사용자 이름
     */
    void deleteGroup(String groupId, String username);

    /**
     * 그룹에 참여합니다.
     * 
     * @param groupId  그룹 ID
     * @param username 사용자 이름
     */
    void joinGroup(String groupId, String username);

    /**
     * 그룹에서 탈퇴합니다.
     * 
     * @param groupId  그룹 ID
     * @param username 사용자 이름
     */
    void leaveGroup(String groupId, String username);

    /**
     * 그룹의 목표를 생성합니다.
     * 
     * @param groupId  그룹 ID
     * @param request  목표 생성 요청 정보
     * @param username 사용자 이름
     * @return 생성된 목표 정보
     */
    GoalResponseDto createGoal(String groupId, GoalCreateRequestDto request, String username);

    /**
     * 그룹의 목표 목록을 조회합니다.
     * 
     * @param groupId 그룹 ID
     * @return 목표 목록
     */
    List<GoalResponseDto> getGoals(String groupId);

    /**
     * 그룹의 목표를 수정합니다.
     * 
     * @param groupId  그룹 ID
     * @param goalId   목표 ID
     * @param request  목표 수정 요청 정보
     * @param username 사용자 이름
     * @return 수정된 목표 정보
     */
    GoalResponseDto updateGoal(String groupId, Long goalId, GoalUpdateRequestDto request, String username);

    /**
     * 그룹의 목표를 삭제합니다.
     * 
     * @param groupId  그룹 ID
     * @param goalId   목표 ID
     * @param username 사용자 이름
     */
    void deleteGoal(String groupId, Long goalId, String username);
}