package com.unity.tribe.domain.admin.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.admin.docs.GetGroups;
import com.unity.tribe.domain.admin.docs.GetReports;
import com.unity.tribe.domain.admin.docs.GetUsers;
import com.unity.tribe.domain.admin.dto.request.BannerAddRequestDto;
import com.unity.tribe.domain.admin.dto.request.BannerUpdateRequestDto;
import com.unity.tribe.domain.admin.dto.request.ManagerAddRequestDto;
import com.unity.tribe.domain.admin.dto.response.AdminResponseDto;
import com.unity.tribe.domain.admin.dto.response.ReportResponseDto;
import com.unity.tribe.domain.admin.dto.response.UserResponseDto;
import com.unity.tribe.domain.banner.dto.response.BannerResponseDto;
import com.unity.tribe.domain.group.dto.response.GroupResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "관리자 BO API")
@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    /**
     * 모임 관리 화면
     */
    @GetMapping("/users")
    @GetUsers
    public ResponseEntity<CommonPageDto<UserResponseDto>> searchUsers(@RequestParam String keyword, Pageable pageable) {
        // TODO 서비스 구현
        CommonPageDto<UserResponseDto> response = new CommonPageDto<>();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "BO 특정 유저 조회")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable String userId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

    @PatchMapping("/users/{userId}")
    @Operation(summary = "BO 특정 유저 수정")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable String userId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "BO 특정 유저 삭제")
    public ResponseEntity<UserResponseDto> deleteUserById(@PathVariable String userId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

    /**
     * 모임 관리 화면
     */
    @GetMapping("/groups")
    @GetGroups
    @Operation(summary = "BO 모임 리스트 조회")
    public ResponseEntity<CommonPageDto<GroupResponseDto>> searchGroups(@RequestParam String keyword, Pageable pageable) {
        // TODO 서비스 구현
        CommonPageDto<GroupResponseDto> response = new CommonPageDto<>();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/groups/{groupId}")
    @Operation(summary = "BO 특정 모임 조회")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable String groupId, String keyword) {
        // TODO 서비스 구현
        return ResponseEntity.ok(GroupResponseDto.builder().build());
    }

    @PatchMapping("/groups/{groupId}")
    @Operation(summary = "BO 특정 모임 수정")
    public ResponseEntity<GroupResponseDto> updateGroupById(@PathVariable String groupId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(GroupResponseDto.builder().build());
    }

    @DeleteMapping("/groups/{groupId}")
    @Operation(summary = "BO 특정 모임 삭제")
    public ResponseEntity<UserResponseDto> deleteGroupById(@PathVariable String groupId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(UserResponseDto.builder().build());
    }

    /**
     * 신고 관리 화면
     */
    @GetMapping("/reports")
    @GetReports
    public ResponseEntity<CommonPageDto<ReportResponseDto>> searchReports(@RequestParam String keyword, Pageable pageable) {
        // TODO 서비스 구현
        CommonPageDto<ReportResponseDto> response = new CommonPageDto<ReportResponseDto>();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reports/{reportId}")
    @Operation(summary = "BO 특정 신고 조회")
    public ResponseEntity<ReportResponseDto> getReportById(@PathVariable String reportId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(ReportResponseDto.builder().build());
    }

    @PatchMapping("/reports/{reportId}")
    @Operation(summary = "BO 특정 신고 수정")
    public ResponseEntity<ReportResponseDto> updateReportById(@PathVariable String reportId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(ReportResponseDto.builder().build());
    }

    @DeleteMapping("/reports/{reportId}")
    @Operation(summary = "BO 특정 신고 삭제")
    public ResponseEntity<ReportResponseDto> deleteReportById(@PathVariable String reportId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(ReportResponseDto.builder().build());
    }

    /**
     * 회원 관리 화면
     */
    @GetMapping("/managers")
    @Operation(summary = "BO 관리자 리스트 조회")
    public ResponseEntity<CommonPageDto<AdminResponseDto>> searchManagers(@RequestParam String keyword, Pageable pageable) {
        // TODO 서비스 구현
        CommonPageDto<AdminResponseDto> response = new CommonPageDto<>();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/managers/{managerId}")
    @Operation(summary = "BO 관리자 리스트 조회")
    public ResponseEntity<CommonPageDto<AdminResponseDto>> searchManagerById(@PathVariable String managerId) {
        // TODO 서비스 구현
        CommonPageDto<AdminResponseDto> response = new CommonPageDto<>();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/managers")
    @Operation(summary = "BO 관리자 추가")
    public ResponseEntity<AdminResponseDto> addManager(@RequestBody ManagerAddRequestDto requestDto) {
        // TODO 서비스 구현
        return ResponseEntity.ok(AdminResponseDto.builder().build());
    }

    @PatchMapping("/managers/{managerId}")
    @Operation(summary = "BO 관리자 수정")
    public ResponseEntity<AdminResponseDto> updateManager(@PathVariable String managerId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(AdminResponseDto.builder().build());
    }

    @DeleteMapping("/managers/{managerId}")
    @Operation(summary = "BO 관리자 수정")
    public ResponseEntity<AdminResponseDto> deleteManager(@PathVariable String managerId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(AdminResponseDto.builder().build());
    }

    @GetMapping("/banners")
    @Operation(summary = "BO 배너 리스트 조회")
    public ResponseEntity<CommonPageDto<BannerResponseDto>> searchBanners(@RequestParam String keyword, Pageable pageable) {
        // TODO 서비스 구현
        CommonPageDto<BannerResponseDto> response = new CommonPageDto<>();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/banners/{bannerId}")
    @Operation(summary = "BO 특정 배너 조회")
    public ResponseEntity<BannerResponseDto> searchBannerById(@PathVariable String bannerId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(BannerResponseDto.builder().build());
    }

    @PostMapping("/banners")
    @Operation(summary = "BO 배너 추가")
    public ResponseEntity<BannerResponseDto> addBanner(@RequestBody BannerAddRequestDto requestDto) {
        // TODO 서비스 구현
        return ResponseEntity.ok(BannerResponseDto.builder().build());
    }

    @PatchMapping("/banners/{bannerId}")
    @Operation(summary = "BO 배너 수정")
    public ResponseEntity<BannerResponseDto> updateBanner(@PathVariable String bannerId, @RequestBody BannerUpdateRequestDto requestDto) {
        // TODO 서비스 구현
        return ResponseEntity.ok(BannerResponseDto.builder().build());
    }

    @DeleteMapping("/banners/{bannerId}")
    @Operation(summary = "BO 배너 삭제")
    public ResponseEntity<BannerResponseDto> deleteBanner(@PathVariable String bannerId) {
        // TODO 서비스 구현
        return ResponseEntity.ok(BannerResponseDto.builder().build());
    }


}
