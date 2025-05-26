package com.unity.tribe.domain.group.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.group.docs.CreateGoal;
import com.unity.tribe.domain.group.docs.DeleteGoal;
import com.unity.tribe.domain.group.docs.GetGoals;
import com.unity.tribe.domain.group.docs.GoalApi;
import com.unity.tribe.domain.group.docs.UpdateGoal;
import com.unity.tribe.domain.group.dto.request.GoalCreateRequestDto;
import com.unity.tribe.domain.group.dto.request.GoalUpdateRequestDto;
import com.unity.tribe.domain.group.dto.response.GoalResponseDto;
import com.unity.tribe.domain.group.service.GroupService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 모임 목표 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@GoalApi
public class GoalController {

    private final GroupService groupService;

    /**
     * 모임에 새로운 목표를 생성합니다.
     * 
     * @param groupId     모임 ULID
     * @param request     목표 생성 요청 정보
     * @param userDetails 인증된 사용자 정보
     * @return 생성된 목표 정보
     */
    @PostMapping("/{groupId}/goals")
    @CreateGoal
    public ResponseEntity<ApiResponseDto<GoalResponseDto>> createGoal(
            @Parameter(name = "groupId", description = "모임 ULID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "01HGW2N8G9PZ95HQ3K7GQWX9TF")) @PathVariable String groupId,
            @Valid @RequestBody GoalCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        GoalResponseDto goal = groupService.createGoal(groupId, request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("목표가 성공적으로 생성되었습니다.", goal));
    }

    /**
     * 모임의 모든 목표를 조회합니다.
     * 
     * @param groupId 모임 ULID
     * @return 목표 목록
     */
    @GetMapping("/{groupId}/goals")
    @GetGoals
    public ResponseEntity<ApiResponseDto<List<GoalResponseDto>>> getGoals(
            @Parameter(name = "groupId", description = "모임 ULID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "01HGW2N8G9PZ95HQ3K7GQWX9TF")) @PathVariable String groupId) {

        List<GoalResponseDto> goals = groupService.getGoals(groupId);
        return ResponseEntity.ok(ApiResponseDto.success(goals));
    }

    /**
     * 모임의 특정 목표를 수정합니다.
     * 
     * @param groupId     모임 ULID
     * @param goalId      수정할 목표 ID
     * @param request     수정할 목표 정보
     * @param userDetails 인증된 사용자 정보
     * @return 수정된 목표 정보
     */
    @PutMapping("/{groupId}/goals/{goalId}")
    @UpdateGoal
    public ResponseEntity<ApiResponseDto<GoalResponseDto>> updateGoal(
            @Parameter(name = "groupId", description = "모임 ULID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "01HGW2N8G9PZ95HQ3K7GQWX9TF")) @PathVariable String groupId,
            @Parameter(name = "goalId", description = "목표 ID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer", example = "1")) @PathVariable Long goalId,
            @Valid @RequestBody GoalUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        GoalResponseDto goal = groupService.updateGoal(groupId, goalId, request,
                userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("목표가 성공적으로 수정되었습니다.", goal));
    }

    /**
     * 모임의 특정 목표를 삭제합니다.
     * 
     * @param groupId     모임 ULID
     * @param goalId      삭제할 목표 ID
     * @param userDetails 인증된 사용자 정보
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{groupId}/goals/{goalId}")
    @DeleteGoal
    public ResponseEntity<ApiResponseDto<Void>> deleteGoal(
            @Parameter(name = "groupId", description = "모임 ULID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "string", example = "01HGW2N8G9PZ95HQ3K7GQWX9TF")) @PathVariable String groupId,
            @Parameter(name = "goalId", description = "목표 ID", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer", example = "1")) @PathVariable Long goalId,
            @AuthenticationPrincipal UserDetails userDetails) {

        groupService.deleteGoal(groupId, goalId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("목표가 성공적으로 삭제되었습니다.", null));
    }
}