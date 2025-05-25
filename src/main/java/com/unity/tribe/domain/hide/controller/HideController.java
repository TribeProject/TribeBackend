package com.unity.tribe.domain.hide.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.domain.hide.docs.CreateHide;
import com.unity.tribe.domain.hide.docs.DeleteHide;
import com.unity.tribe.domain.hide.docs.HideApi;
import com.unity.tribe.domain.hide.dto.request.HideCreateRequestDto;
import com.unity.tribe.domain.hide.dto.response.HideResponseDto;
import com.unity.tribe.domain.hide.entity.HideEntity.TargetType;
import com.unity.tribe.domain.hide.service.HideService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 숨김 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/hides")
@RequiredArgsConstructor
@HideApi
public class HideController {

    private final HideService hideService;

    /**
     * 콘텐츠를 숨김 처리합니다.
     * 
     * @param request     숨김 요청 데이터
     * @param userDetails 인증된 사용자 정보
     * @return 숨김 정보
     */
    @PostMapping
    @CreateHide
    public ResponseEntity<ApiResponseDto<HideResponseDto>> hideContent(
            @Valid @RequestBody HideCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        HideResponseDto hide = hideService.hideContent(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("콘텐츠가 성공적으로 숨김 처리되었습니다.", hide));
    }

    /**
     * 숨김을 해제합니다.
     * 
     * @param targetType  대상 타입
     * @param targetId    대상 ID
     * @param userDetails 인증된 사용자 정보
     * @return 해제 성공 메시지
     */
    @DeleteMapping("/{targetType}/{targetId}")
    @DeleteHide
    public ResponseEntity<ApiResponseDto<Void>> unhideContent(
            @Parameter(description = "대상 타입") @PathVariable TargetType targetType,
            @Parameter(description = "대상 ID") @PathVariable String targetId,
            @AuthenticationPrincipal UserDetails userDetails) {

        hideService.unhideContent(targetType, targetId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("숨김이 성공적으로 해제되었습니다.", null));
    }
}