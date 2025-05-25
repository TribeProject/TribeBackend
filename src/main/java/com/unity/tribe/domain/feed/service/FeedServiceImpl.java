package com.unity.tribe.domain.feed.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.feed.dto.request.FeedCreateRequestDto;
import com.unity.tribe.domain.feed.dto.request.FeedUpdateRequestDto;
import com.unity.tribe.domain.feed.dto.response.FeedResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    // TODO: 구현 필요
    @Override
    public CommonPageDto<List<FeedResponseDto>> getFeeds(String groupId, Pageable pageable) {
        return null;
    }

    // TODO: 구현 필요
    @Override
    public CommonPageDto<List<FeedResponseDto>> getUserFeeds(String userId, Pageable pageable) {
        return null;
    }

    // TODO: 구현 필요
    @Override
    public FeedResponseDto createFeed(String groupId, FeedCreateRequestDto request,
            String userId) {
        return null;
    }

    // TODO: 구현 필요
    @Override
    public FeedResponseDto updateFeed(Long feedId, FeedUpdateRequestDto request,
            String userId) {
        return null;
    }

    // TODO: 구현 필요
    @Override
    public void deleteFeed(Long feedId, String userId) {
    }

    // TODO: 구현 필요
    @Override
    public CommonPageDto<List<FeedResponseDto>> searchFeeds(String groupId, String keyword,
            Pageable pageable) {
        return null;
    }
}