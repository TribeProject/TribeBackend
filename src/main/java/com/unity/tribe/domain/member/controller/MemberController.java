package com.unity.tribe.domain.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.member.docs.ChangeMemberRole;
import com.unity.tribe.domain.member.docs.GetMembers;
import com.unity.tribe.domain.member.docs.KickMember;
import com.unity.tribe.domain.member.docs.MemberApi;
import com.unity.tribe.domain.member.dto.request.MemberRoleUpdateRequestDto;
import com.unity.tribe.domain.member.dto.response.MemberResponseDto;
import com.unity.tribe.domain.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 모임 멤버 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/groups/{groupId}/members")
@RequiredArgsConstructor
@MemberApi
public class MemberController {

    private final MemberService memberService;

    /**
     * 그룹의 모든 멤버 목록을 조회합니다.
     */
    @GetMapping
    @GetMembers
    public ResponseEntity<ApiResponseDto<List<MemberResponseDto>>> getMembers(
            @PathVariable String groupId,
            @AuthenticationPrincipal UserDetails userDetails) {

        List<MemberResponseDto> members = memberService.getMembers(groupId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success(members));
    }

    /**
     * 모임 멤버를 강제 탈퇴시킵니다.
     */
    @DeleteMapping("/{memberId}")
    @KickMember
    public ResponseEntity<ApiResponseDto<Void>> kickMember(
            @PathVariable String groupId,
            @PathVariable String memberId,
            @AuthenticationPrincipal UserDetails userDetails) {

        memberService.kickMember(groupId, memberId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("멤버를 성공적으로 강제 탈퇴시켰습니다.", null));
    }

    /**
     * 모임 멤버의 역할을 변경합니다.
     */
    @PutMapping("/{memberId}/role")
    @ChangeMemberRole
    public ResponseEntity<ApiResponseDto<Void>> changeMemberRole(
            @PathVariable String groupId,
            @PathVariable String memberId,
            @Valid @RequestBody MemberRoleUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        memberService.changeMemberRole(groupId, memberId, request.getRole(), userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("멤버 역할이 성공적으로 변경되었습니다.", null));
    }
}