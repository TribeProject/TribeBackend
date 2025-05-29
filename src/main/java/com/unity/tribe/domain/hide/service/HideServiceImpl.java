package com.unity.tribe.domain.hide.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.domain.hide.dto.request.HideCreateRequestDto;
import com.unity.tribe.domain.hide.dto.response.HideResponseDto;
import com.unity.tribe.domain.hide.entity.HideEntity.TargetType;

@Service
@Transactional
public class HideServiceImpl implements HideService {

    @Override
    public HideResponseDto hideContent(HideCreateRequestDto request, String userId) {
        // TODO: 구현 필요
        return null;
    }

    @Override
    public void unhideContent(TargetType targetType, String targetId, String userId) {
        // TODO: 구현 필요
    }

    @Override
    public boolean isHidden(String userId, TargetType targetType, String targetId) {
        // TODO: 구현 필요
        return false;
    }
}