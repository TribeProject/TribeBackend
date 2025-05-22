package com.unity.tribe.domain.admin.controller;


import com.unity.tribe.domain.group.dto.GroupResponseDto;
import com.unity.tribe.domain.admin.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Tag(name = "관리자 BO API")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/users")
    @Operation(summary = "BO 유저 목록 조회")
    public ResponseEntity<List<UserResponseDto>> searchUsers(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(Collections.singletonList(UserResponseDto.builder().build()));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "BO 특정 유저 조회")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

    @PatchMapping("/users/{userId}")
    @Operation(summary = "BO 특정 유저 수정")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable String userId) {
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "BO 특정 유저 수정")
    public ResponseEntity<UserResponseDto> deleteUserById(@PathVariable String userId) {
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }
    @GetMapping("/groups")
    @Operation(summary = "BO 모임 목록 조회")
    public ResponseEntity<List<GroupResponseDto>> searchGroups(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(Collections.singletonList(GroupResponseDto.builder().build()));
    }

    @GetMapping("/groups/{groupId}")
    @Operation(summary = "BO 모임 목록 조회")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable String groupId) {
        return ResponseEntity.ok(GroupResponseDto.builder().build());
    }

    @PatchMapping("/groups/{groupId}")
    @Operation(summary = "BO 특정 모임 수정")
    public ResponseEntity<GroupResponseDto> updateGroupById(@PathVariable String groupId) {
        return ResponseEntity.ok(GroupResponseDto.builder().build());
    }

    @DeleteMapping("/groups/{groupId}")
    @Operation(summary = "BO 특정 모임 삭제")
    public ResponseEntity<UserResponseDto> deleteGroupById(@PathVariable String groupId) {
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

}
