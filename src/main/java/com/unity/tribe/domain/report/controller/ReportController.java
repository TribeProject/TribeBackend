package com.unity.tribe.domain.report.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.report.docs.CreateReport;
import com.unity.tribe.domain.report.docs.ReportApi;
import com.unity.tribe.domain.report.dto.ReportCreateRequestDto;
import com.unity.tribe.domain.report.service.ReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 신고 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@ReportApi
public class ReportController {

    private final ReportService reportService; // 신고 관련 비즈니스 로직을 처리하는 서비스

    /**
     * 신고를 생성합니다.
     * 
     * @param request     신고 생성 요청 DTO
     * @param userDetails 인증된 사용자 정보
     * @return 신고 생성 결과 응답
     */
    @PostMapping
    @CreateReport
    public ResponseEntity<ApiResponseDto<Void>> createReport(
            @Valid @RequestBody ReportCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        reportService.createReport(request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("신고가 정상적으로 접수되었습니다.", null));
    }
}