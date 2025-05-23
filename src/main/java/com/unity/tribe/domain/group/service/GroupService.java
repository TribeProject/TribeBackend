package com.unity.tribe.domain.group.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.group.dto.request.GoalCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GoalUpdateRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupUpdateRequestDto;
import com.unity.tribe.domain.group.dto.response.GoalResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupDetailResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupListResponseDto;

/**
 * 모임 관련 서비스
 */
public interface GroupService {

    /**
     * 상태별 모임 목록을 페이지네이션하여 조회
     * 
     * @param pageable 페이지 정보
     * @param status   모임 상태
     * @return 페이지네이션된 모임 목록
     */
    CommonPageDto<List<GroupListResponseDto>> getGroups(Pageable pageable, String status);

    /**
     * 카테고리별 모임 목록을 페이지네이션하여 조회
     * 
     * @param categoryId 카테고리 ID
     * @param pageable   페이지 정보
     * @param status     모임 상태
     * @return 페이지네이션된 모임 목록
     */
    CommonPageDto<List<GroupListResponseDto>> getGroupsByCategory(Long categoryId, Pageable pageable, String status);

    /**
     * 키워드로 모임 검색
     * 
     * @param keyword  검색 키워드
     * @param pageable 페이지 정보
     * @return 검색된 모임 목록
     */
    CommonPageDto<List<GroupListResponseDto>> searchGroups(String keyword, Pageable pageable);

    /**
     * 특정 모임 상세 정보 조회
     * 
     * @param groupId 모임 ID
     * @return 모임 상세 정보
     */
    GroupDetailResponseDto getGroup(Long groupId);

    /**
     * 새로운 모임 생성
     * 
     * @param request 모임 생성 요청 정보
     * @param userId  생성자 ID
     * @return 생성된 모임 정보
     */
    GroupDetailResponseDto createGroup(GroupCreateRequestDto request, String userId);

    /**
     * 모임 정보 수정
     * 
     * @param groupId 모임 ID
     * @param request 수정 요청 정보
     * @param userId  수정자 ID
     * @return 수정된 모임 정보
     */
    GroupDetailResponseDto updateGroup(Long groupId, GroupUpdateRequestDto request, String userId);

    /**
     * 모임 삭제
     * 
     * @param groupId 모임 ID
     * @param userId  삭제 요청자 ID
     */
    void deleteGroup(Long groupId, String userId);

    /**
     * 모임 가입
     * 
     * @param groupId 모임 ID
     * @param userId  가입 요청자 ID
     */
    void joinGroup(Long groupId, String userId);

    /**
     * 모임 탈퇴
     * 
     * @param groupId 모임 ID
     * @param userId  탈퇴 요청자 ID
     */
    void leaveGroup(Long groupId, String userId);

    /**
     * 모임 목표 생성
     * 
     * @param groupId 모임 ID
     * @param request 목표 생성 요청 정보
     * @param userId  생성자 ID
     * @return 생성된 목표 정보
     */
    GoalResponseDto createGoal(Long groupId, GoalCreateRequestDto request, String userId);

    /**
     * 모임의 목표 목록 조회
     * 
     * @param groupId 모임 ID
     * @return 목표 목록
     */
    List<GoalResponseDto> getGoals(Long groupId);

    /**
     * 모임 목표 수정
     * 
     * @param groupId 모임 ID
     * @param goalId  목표 ID
     * @param request 수정 요청 정보
     * @param userId  수정자 ID
     * @return 수정된 목표 정보
     */
    GoalResponseDto updateGoal(Long groupId, Long goalId, GoalUpdateRequestDto request, String userId);

    /**
     * 모임 목표 삭제
     * 
     * @param groupId 모임 ID
     * @param goalId  목표 ID
     * @param userId  삭제 요청자 ID
     */
    void deleteGoal(Long groupId, Long goalId, String userId);
}