package com.unity.tribe.domain.group.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.group.dto.request.*;
import com.unity.tribe.domain.group.dto.response.GoalResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupDetailResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupListResponseDto;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Override
    public CommonPageDto<List<GroupListResponseDto>> getGroups(Pageable pageable, String status) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public CommonPageDto<List<GroupListResponseDto>> getGroupsByCategory(Long categoryId, Pageable pageable,
            String status) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public CommonPageDto<List<GroupListResponseDto>> searchGroups(String keyword, Pageable pageable) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public GroupDetailResponseDto getGroup(String groupId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public GroupDetailResponseDto createGroup(GroupCreateRequestDto request, String userId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public GroupDetailResponseDto updateGroup(String groupId, GroupUpdateRequestDto request, String userId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public void deleteGroup(String groupId, String userId) {
        // TODO: 구현 필요
    }

    @Override
    public void joinGroup(String groupId, String userId) {
        // TODO: 구현 필요
    }

    @Override
    public void leaveGroup(String groupId, String userId) {
        // TODO: 구현 필요
    }

    @Override
    public GoalResponseDto createGoal(String groupId, GoalCreateRequestDto request, String userId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public List<GoalResponseDto> getGoals(String groupId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public GoalResponseDto updateGoal(String groupId, Long goalId, GoalUpdateRequestDto request, String userId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public void deleteGoal(String groupId, Long goalId, String userId) {
        // TODO: 구현 필요
    }

    @Override
    public CommonPageDto<List<GroupListResponseDto>> getGroupsWithFilter(Pageable pageable,
            GroupFilterRequestDto filter) {
        // TODO: 구현 필요
        return null;
    }
}