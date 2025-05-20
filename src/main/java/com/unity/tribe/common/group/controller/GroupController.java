package com.unity.tribe.common.group.controller;

import com.unity.tribe.common.group.dto.GroupResponseDto;
import com.unity.tribe.core.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@Tag(name = "모임 API")
@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @GetMapping("{groupId}")
    @Operation(summary = "키워드별 모임 조회")
    public ResponseEntity<ApiResponseDto<GroupResponseDto>> getGroup(@PathVariable String groupId) {
        return ResponseEntity.ok(ApiResponseDto.success(GroupResponseDto.builder().build()));
    }

    @GetMapping
    @Operation(summary = "전체 모임 조회")
    public ResponseEntity<ApiResponseDto<List<GroupResponseDto>>> getGroup() {
        return ResponseEntity.ok(ApiResponseDto.success(Collections.singletonList(GroupResponseDto.builder().build())));

    }

}
