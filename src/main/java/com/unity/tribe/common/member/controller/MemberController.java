package com.unity.tribe.common.member.controller;

import com.unity.tribe.common.member.dto.MemberResponseDto;
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

@Tag(name = "모임 회원 API")
@RestController
@RequestMapping("/api/members")
public class MemberController {

    @GetMapping
    @Operation(summary = "전체 멤버 조회")
    public ResponseEntity<ApiResponseDto<List<MemberResponseDto>>> getMembers() {
        return ResponseEntity.ok(ApiResponseDto.success(Collections.singletonList(MemberResponseDto.builder().build())));
    }

    @GetMapping("{memberId}")
    @Operation(summary = "키워드 멤버 조회")
    public ResponseEntity<ApiResponseDto<List<MemberResponseDto>>> getMembers(@PathVariable String memberId) {
        return ResponseEntity.ok(ApiResponseDto.success(Collections.singletonList(MemberResponseDto.builder().build())));
    }
}
