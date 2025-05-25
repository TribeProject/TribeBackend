package com.unity.tribe.domain.hide.service;

import com.unity.tribe.domain.hide.dto.request.HideCreateRequestDto;
import com.unity.tribe.domain.hide.dto.response.HideResponseDto;
import com.unity.tribe.domain.hide.entity.HideEntity.TargetType;

public interface HideService {

    /**
     * 콘텐츠를 숨김 처리합니다.
     * 
     * @param request 숨김 요청 정보
     * @param userId  사용자 ID
     * @return 숨김 정보
     */
    HideResponseDto hideContent(HideCreateRequestDto request, String userId);

    /**
     * 숨김을 해제합니다.
     * 
     * @param targetType 대상 타입
     * @param targetId   대상 ID
     * @param userId     사용자 ID
     */
    void unhideContent(TargetType targetType, String targetId, String userId);

    /**
     * 숨김 여부를 확인합니다.
     * 
     * @param userId     사용자 ID
     * @param targetType 대상 타입
     * @param targetId   대상 ID
     * @return 숨김 여부
     */
    boolean isHidden(String userId, TargetType targetType, String targetId);
}