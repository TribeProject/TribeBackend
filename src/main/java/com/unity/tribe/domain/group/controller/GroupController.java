package com.unity.tribe.domain.group.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.CreateGoal;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.CreateGroup;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.DeleteGoal;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.DeleteGroup;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.GetGoals;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.GetGroup;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.GetGroups;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.GetGroupsByCategory;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.JoinGroup;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.LeaveGroup;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.UpdateGoal;
import com.unity.tribe.domain.group.annotation.GroupSwaggerAnnotation.UpdateGroup;
import com.unity.tribe.domain.group.dto.request.GoalCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GoalUpdateRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GroupUpdateRequestDto;
import com.unity.tribe.domain.group.dto.response.GoalResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupDetailResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupListResponseDto;
import com.unity.tribe.domain.group.service.GroupService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@GroupSwaggerAnnotation.GroupApi
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    @GetGroups
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<GroupListResponseDto>>>> getGroups(
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) String status) {

        CommonPageDto<List<GroupListResponseDto>> groups = groupService.getGroups(pageable, status);
        return ResponseEntity.ok(ApiResponseDto.success(groups));
    }

    @GetMapping("/category/{categoryId}")
    @GetGroupsByCategory
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<GroupListResponseDto>>>> getGroupsByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 20) Pageable pageable,
            @RequestParam(required = false) String status) {

        CommonPageDto<List<GroupListResponseDto>> groups = groupService.getGroupsByCategory(categoryId, pageable,
                status);
        return ResponseEntity.ok(ApiResponseDto.success(groups));
    }

    @GetMapping("/search")
    @GetGroup
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<GroupListResponseDto>>>> searchGroups(
            @RequestParam String keyword,
            @PageableDefault(size = 20) Pageable pageable) {

        CommonPageDto<List<GroupListResponseDto>> groups = groupService.searchGroups(keyword, pageable);
        return ResponseEntity.ok(ApiResponseDto.success(groups));
    }

    @GetMapping("/{groupId}")
    @GetGroup
    public ResponseEntity<ApiResponseDto<GroupDetailResponseDto>> getGroup(@PathVariable Long groupId) {
        GroupDetailResponseDto group = groupService.getGroup(groupId);
        return ResponseEntity.ok(ApiResponseDto.success(group));
    }

    @PostMapping
    @CreateGroup
    public ResponseEntity<ApiResponseDto<GroupDetailResponseDto>> createGroup(
            @Valid @RequestBody GroupCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        GroupDetailResponseDto group = groupService.createGroup(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("그룹이 성공적으로 생성되었습니다.", group));
    }

    @PutMapping("/{groupId}")
    @UpdateGroup
    public ResponseEntity<ApiResponseDto<GroupDetailResponseDto>> updateGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody GroupUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        GroupDetailResponseDto group = groupService.updateGroup(groupId, request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("그룹이 성공적으로 수정되었습니다.", group));
    }

    @DeleteMapping("/{groupId}")
    @DeleteGroup
    public ResponseEntity<ApiResponseDto<Void>> deleteGroup(
            @PathVariable Long groupId,
            @AuthenticationPrincipal UserDetails userDetails) {

        groupService.deleteGroup(groupId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("그룹이 성공적으로 삭제되었습니다.", null));
    }

    @PostMapping("/{groupId}/join")
    @JoinGroup
    public ResponseEntity<ApiResponseDto<Void>> joinGroup(
            @PathVariable Long groupId,
            @AuthenticationPrincipal UserDetails userDetails) {

        groupService.joinGroup(groupId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("그룹에 성공적으로 참여했습니다.", null));
    }

    @DeleteMapping("/{groupId}/leave")
    @LeaveGroup
    public ResponseEntity<ApiResponseDto<Void>> leaveGroup(
            @PathVariable Long groupId,
            @AuthenticationPrincipal UserDetails userDetails) {

        groupService.leaveGroup(groupId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("그룹에서 성공적으로 탈퇴했습니다.", null));
    }

    @PostMapping("/{groupId}/goals")
    @CreateGoal
    public ResponseEntity<ApiResponseDto<GoalResponseDto>> createGoal(
            @PathVariable Long groupId,
            @Valid @RequestBody GoalCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        GoalResponseDto goal = groupService.createGoal(groupId, request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("목표가 성공적으로 생성되었습니다.", goal));
    }

    @GetMapping("/{groupId}/goals")
    @GetGoals
    public ResponseEntity<ApiResponseDto<List<GoalResponseDto>>> getGoals(@PathVariable Long groupId) {
        List<GoalResponseDto> goals = groupService.getGoals(groupId);
        return ResponseEntity.ok(ApiResponseDto.success(goals));
    }

    @PutMapping("/{groupId}/goals/{goalId}")
    @UpdateGoal
    public ResponseEntity<ApiResponseDto<GoalResponseDto>> updateGoal(
            @PathVariable Long groupId,
            @PathVariable Long goalId,
            @Valid @RequestBody GoalUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        GoalResponseDto goal = groupService.updateGoal(groupId, goalId, request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("목표가 성공적으로 수정되었습니다.", goal));
    }

    @DeleteMapping("/{groupId}/goals/{goalId}")
    @DeleteGoal
    public ResponseEntity<ApiResponseDto<Void>> deleteGoal(
            @PathVariable Long groupId,
            @PathVariable Long goalId,
            @AuthenticationPrincipal UserDetails userDetails) {

        groupService.deleteGoal(groupId, goalId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("목표가 성공적으로 삭제되었습니다.", null));
    }
}
