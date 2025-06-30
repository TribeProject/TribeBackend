package com.unity.tribe.domain.feed.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeApiException;
import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.feed.dto.request.FeedCreateRequestDto;
import com.unity.tribe.domain.feed.dto.request.FeedUpdateRequestDto;
import com.unity.tribe.domain.feed.dto.response.FeedResponseDto;
import com.unity.tribe.domain.feed.entity.FeedEntity;
import com.unity.tribe.domain.feed.entity.FeedEntity.FeedStatus;
import com.unity.tribe.domain.feed.entity.FeedEntity.FeedType;
import com.unity.tribe.domain.feed.repository.FeedRepository;
import com.unity.tribe.domain.user.entity.UserEntity;
import com.unity.tribe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<FeedResponseDto>> getFeeds(String groupId, Pageable pageable) {
        Page<FeedEntity> feedPage = feedRepository.findAllByGroupIdAndStatus(groupId, FeedStatus.ACTIVE, pageable);

        List<FeedResponseDto> feedDtos = feedPage.getContent().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        return CommonPageDto.<List<FeedResponseDto>>builder()
                .data(feedDtos)
                .totalElements(feedPage.getTotalElements())
                .totalPages(feedPage.getTotalPages())
                .page(feedPage.getNumber())
                .size(feedPage.getSize())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<FeedResponseDto>> getUserFeeds(String userId, Pageable pageable) {
        // 사용자 존재 확인
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.USER_NOT_FOUND));

        Page<FeedEntity> feedPage = feedRepository.findAllByUserId(userId, pageable);

        List<FeedResponseDto> feedDtos = feedPage.getContent().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        return CommonPageDto.<List<FeedResponseDto>>builder()
                .data(feedDtos)
                .totalElements(feedPage.getTotalElements())
                .totalPages(feedPage.getTotalPages())
                .page(feedPage.getNumber())
                .size(feedPage.getSize())
                .build();
    }

    @Override
    public FeedResponseDto createFeed(String groupId, FeedCreateRequestDto request, String userId) {
        // 사용자 존재 확인
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.USER_NOT_FOUND));

        // 피드 엔티티 생성
        FeedEntity feedEntity = FeedEntity.builder()
                .groupId(groupId)
                .userId(userId)
                .feedType(FeedType.valueOf(request.getFeedType()))
                .contentText(request.getContent())
                .image(request.getImage())
                .status(FeedStatus.ACTIVE)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        FeedEntity savedFeed = feedRepository.save(feedEntity);
        return convertToResponseDto(savedFeed);
    }

    @Override
    public FeedResponseDto updateFeed(Long feedId, FeedUpdateRequestDto request, String userId) {
        FeedEntity feedEntity = feedRepository.findById(feedId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.FEED_NOT_FOUND));

        // 작성자 확인
        if (!feedEntity.getUserId().equals(userId)) {
            throw new TribeApiException(ErrorCode.FEED_ACCESS_DENIED);
        }

        // 삭제된 피드인지 확인
        if (feedEntity.getStatus() == FeedStatus.DELETED) {
            throw new TribeApiException(ErrorCode.FEED_ALREADY_DELETED);
        }

        // 피드 업데이트
        if (request.getContent() != null) {
            feedEntity.setContentText(request.getContent());
        }
        if (request.getImage() != null) {
            feedEntity.setImage(request.getImage());
        }
        feedEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        FeedEntity updatedFeed = feedRepository.save(feedEntity);
        return convertToResponseDto(updatedFeed);
    }

    @Override
    public void deleteFeed(Long feedId, String userId) {
        FeedEntity feedEntity = feedRepository.findById(feedId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.FEED_NOT_FOUND));

        // 작성자 확인
        if (!feedEntity.getUserId().equals(userId)) {
            throw new TribeApiException(ErrorCode.FEED_ACCESS_DENIED);
        }

        // 이미 삭제된 피드인지 확인
        if (feedEntity.getStatus() == FeedStatus.DELETED) {
            throw new TribeApiException(ErrorCode.FEED_ALREADY_DELETED);
        }

        // 소프트 삭제
        feedEntity.setStatus(FeedStatus.DELETED);
        feedEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        feedRepository.save(feedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<FeedResponseDto>> searchFeeds(String groupId, String keyword, Pageable pageable) {
        Page<FeedEntity> feedPage = feedRepository.searchByKeyword(groupId, keyword, pageable);

        List<FeedResponseDto> feedDtos = feedPage.getContent().stream()
                .filter(feed -> feed.getStatus() == FeedStatus.ACTIVE) // 활성 피드만 필터링
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        return CommonPageDto.<List<FeedResponseDto>>builder()
                .data(feedDtos)
                .totalElements(feedPage.getTotalElements())
                .totalPages(feedPage.getTotalPages())
                .page(feedPage.getNumber())
                .size(feedPage.getSize())
                .build();
    }

    private FeedResponseDto convertToResponseDto(FeedEntity entity) {
        return FeedResponseDto.builder()
                .feedId(entity.getFeedId())
                .groupId(entity.getGroupId())
                .userId(entity.getUserId())
                .feedType(entity.getFeedType().name())
                .content(entity.getContentText())
                .image(entity.getImage())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt().toLocalDateTime())
                .updatedAt(entity.getUpdatedAt().toLocalDateTime())
                .build();
    }
}