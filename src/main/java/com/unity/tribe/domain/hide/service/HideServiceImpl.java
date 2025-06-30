package com.unity.tribe.domain.hide.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeApiException;
import com.unity.tribe.domain.hide.dto.request.HideCreateRequestDto;
import com.unity.tribe.domain.hide.dto.response.HideResponseDto;
import com.unity.tribe.domain.hide.entity.HideEntity;
import com.unity.tribe.domain.hide.entity.HideEntity.TargetType;
import com.unity.tribe.domain.hide.repository.HideRepository;
import com.unity.tribe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HideServiceImpl implements HideService {

    private final HideRepository hideRepository;
    private final UserRepository userRepository;

    @Override
    public HideResponseDto hideContent(HideCreateRequestDto request, String userId) {
        // 사용자 검증
        if (!userRepository.existsById(userId)) {
            throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
        }

        // 이미 숨김 처리된 콘텐츠인지 확인
        if (hideRepository.existsByUserIdAndTargetTypeAndTargetId(
                userId, request.getTargetType(), request.getTargetId())) {
            throw new TribeApiException(ErrorCode.ALREADY_HIDDEN);
        }

        // 숨김 엔티티 생성
        HideEntity hideEntity = HideEntity.builder()
                .userId(userId)
                .targetType(request.getTargetType())
                .targetId(request.getTargetId())
                .build();

        HideEntity savedHide = hideRepository.save(hideEntity);

        return HideResponseDto.builder()
                .hideId(savedHide.getHideId())
                .userId(savedHide.getUserId())
                .targetType(savedHide.getTargetType())
                .targetId(savedHide.getTargetId())
                .createdAt(savedHide.getCreatedAt())
                .build();
    }

    @Override
    public void unhideContent(TargetType targetType, String targetId, String userId) {
        // 사용자 검증
        if (!userRepository.existsById(userId)) {
            throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
        }

        // 숨김 엔티티 조회
        Optional<HideEntity> hideEntity = hideRepository.findByUserIdAndTargetTypeAndTargetId(
                userId, targetType, targetId);

        if (hideEntity.isEmpty()) {
            throw new TribeApiException(ErrorCode.HIDE_NOT_FOUND);
        }

        hideRepository.delete(hideEntity.get());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isHidden(String userId, TargetType targetType, String targetId) {
        return hideRepository.existsByUserIdAndTargetTypeAndTargetId(userId, targetType, targetId);
    }
}