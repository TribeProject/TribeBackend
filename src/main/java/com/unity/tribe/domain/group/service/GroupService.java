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

public interface GroupService {

    CommonPageDto<List<GroupListResponseDto>> getGroups(Pageable pageable, String status);

    CommonPageDto<List<GroupListResponseDto>> getGroupsByCategory(Long categoryId, Pageable pageable, String status);

    CommonPageDto<List<GroupListResponseDto>> searchGroups(String keyword, Pageable pageable);

    GroupDetailResponseDto getGroup(Long groupId);

    GroupDetailResponseDto createGroup(GroupCreateRequestDto request, String userId);

    GroupDetailResponseDto updateGroup(Long groupId, GroupUpdateRequestDto request, String userId);

    void deleteGroup(Long groupId, String userId);

    void joinGroup(Long groupId, String userId);

    void leaveGroup(Long groupId, String userId);

    GoalResponseDto createGoal(Long groupId, GoalCreateRequestDto request, String userId);

    List<GoalResponseDto> getGoals(Long groupId);

    GoalResponseDto updateGoal(Long groupId, Long goalId, GoalUpdateRequestDto request, String userId);

    void deleteGoal(Long groupId, Long goalId, String userId);
}