package com.unity.tribe.domain.feed.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.feed.dto.request.FeedCreateRequestDto;
import com.unity.tribe.domain.feed.dto.request.FeedUpdateRequestDto;
import com.unity.tribe.domain.feed.dto.response.FeedResponseDto;

public interface FeedService {

    /**
     * 그룹의 피드 목록을 조회합니다.
     * 
     * @param groupId  그룹 ID
     * @param pageable 페이지 정보
     * @return 피드 목록 페이지 정보
     */
    CommonPageDto<List<FeedResponseDto>> getFeeds(String groupId, Pageable pageable);

    /**
     * 사용자의 피드 목록을 조회합니다.
     * 
     * @param userId   사용자 ID
     * @param pageable 페이지 정보
     * @return 피드 목록 페이지 정보
     */
    CommonPageDto<List<FeedResponseDto>> getUserFeeds(String userId, Pageable pageable);

    /**
     * 피드를 생성합니다.
     * 
     * @param groupId 그룹 ID
     * @param request 피드 생성 요청 정보
     * @param userId  사용자 ID
     * @return 생성된 피드 정보
     */
    FeedResponseDto createFeed(String groupId, FeedCreateRequestDto request, String userId);

    /**
     * 피드를 수정합니다.
     * 
     * @param feedId  피드 ID
     * @param request 피드 수정 요청 정보
     * @param userId  사용자 ID
     * @return 수정된 피드 정보
     */
    FeedResponseDto updateFeed(Long feedId, FeedUpdateRequestDto request, String userId);

    /**
     * 피드를 삭제합니다.
     * 
     * @param feedId 피드 ID
     * @param userId 사용자 ID
     */
    void deleteFeed(Long feedId, String userId);

    /**
     * 키워드로 피드를 검색합니다.
     * 
     * @param groupId  그룹 ID
     * @param keyword  검색 키워드
     * @param pageable 페이지 정보
     * @return 검색된 피드 목록 페이지 정보
     */
    CommonPageDto<List<FeedResponseDto>> searchFeeds(String groupId, String keyword, Pageable pageable);
}