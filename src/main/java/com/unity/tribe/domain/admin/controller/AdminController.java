package com.unity.tribe.domain.admin.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unity.tribe.domain.admin.dto.response.UserResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "관리자 BO API")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/users")
    @Operation(summary = "BO 유저 모임 조회")
    public ResponseEntity<List<UserResponseDto>> searchUsers(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(Collections.singletonList(UserResponseDto.builder().build()));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "BO 특정 유저 조회")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

    @PatchMapping("/users/{userId}")
    @Operation(summary = "BO 특정 유저 수정")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable String userId) {
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "BO 특정 유저 삭제")
    public ResponseEntity<UserResponseDto> deleteUserById(@PathVariable String userId) {
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

    @GetMapping("/groups")
    @Operation(summary = "BO 모임 모임 조회")
    public ResponseEntity<List<GroupResponseDto>> searchGroups(@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(Collections.singletonList(GroupResponseDto.builder().build()));
    }

    @GetMapping("/groups/{groupId}")
    @Operation(summary = "BO 모임 모임 조회")
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
