package com.unity.tribe.domain.feed.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unity.tribe.common.model.ApiResponseDto;
import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.feed.docs.CreateFeed;
import com.unity.tribe.domain.feed.docs.DeleteFeed;
import com.unity.tribe.domain.feed.docs.FeedApi;
import com.unity.tribe.domain.feed.docs.GetFeeds;
import com.unity.tribe.domain.feed.docs.GetMyFeeds;
import com.unity.tribe.domain.feed.docs.SearchFeeds;
import com.unity.tribe.domain.feed.docs.UpdateFeed;
import com.unity.tribe.domain.feed.dto.request.FeedCreateRequestDto;
import com.unity.tribe.domain.feed.dto.request.FeedUpdateRequestDto;
import com.unity.tribe.domain.feed.dto.response.FeedResponseDto;
import com.unity.tribe.domain.feed.service.FeedService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 피드 인증 피드 관련 컨트롤러
 */
@RestController
@RequestMapping("/v1/feeds")
@RequiredArgsConstructor
@FeedApi
public class FeedController {

    private final FeedService feedService;

    /**
     * 특정 모임의 인증 피드 목록을 조회합니다.
     * 
     * @param groupId 모임 ULID
     * @param page    페이지 번호 (0부터 시작)
     * @param size    페이지 크기
     * @param sort    정렬 조건 (e.g. createdAt,desc)
     * @return 페이징된 인증 피드 목록
     */
    @GetMapping("/groups/{groupId}")
    @GetFeeds
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<FeedResponseDto>>>> getFeeds(
            @Parameter(description = "모임 ID") @PathVariable String groupId,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "정렬 기준 (e.g. createdAt,desc)") @RequestParam(required = false) String sort) {

        Pageable pageable = createPageable(page, size, sort);
        CommonPageDto<List<FeedResponseDto>> feeds = feedService.getFeeds(groupId,
                pageable);
        return ResponseEntity.ok(ApiResponseDto.success(feeds));
    }

    /**
     * 현재 사용자의 인증 피드 목록을 조회합니다.
     * 
     * @param page        페이지 번호 (0부터 시작)
     * @param size        페이지 크기
     * @param sort        정렬 조건 (e.g. createdAt,desc)
     * @param userDetails 인증된 사용자 정보
     * @return 페이징된 사용자의 인증 피드 목록
     */
    @GetMapping("/users/me")
    @GetMyFeeds
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<FeedResponseDto>>>> getMyFeeds(
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "정렬 기준 (e.g. createdAt,desc)") @RequestParam(required = false) String sort,
            @AuthenticationPrincipal UserDetails userDetails) {

        Pageable pageable = createPageable(page, size, sort);
        CommonPageDto<List<FeedResponseDto>> feeds = feedService
                .getUserFeeds(userDetails.getUsername(), pageable);
        return ResponseEntity.ok(ApiResponseDto.success(feeds));
    }

    /**
     * 새로운 인증 피드를 생성합니다.
     * 
     * @param groupId     모임 ULID
     * @param request     인증 피드 생성 요청 데이터
     * @param userDetails 인증된 사용자 정보
     * @return 생성된 인증 피드 정보
     */
    @PostMapping("/groups/{groupId}")
    @CreateFeed
    public ResponseEntity<ApiResponseDto<FeedResponseDto>> createFeed(
            @Parameter(description = "모임 ID") @PathVariable String groupId,
            @Valid @RequestBody FeedCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        FeedResponseDto feed = feedService.createFeed(groupId, request,
                userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("인증 피드가 성공적으로 생성되었습니다.", feed));
    }

    /**
     * 기존 인증 피드 정보를 수정합니다.
     * 
     * @param feedId      수정할 인증 피드 ID
     * @param request     수정할 인증 피드 정보
     * @param userDetails 인증된 사용자 정보
     * @return 수정된 인증 피드 정보
     */
    @PutMapping("/{feedId}")
    @UpdateFeed
    public ResponseEntity<ApiResponseDto<FeedResponseDto>> updateFeed(
            @Parameter(description = "인증 피드 ID") @PathVariable Long feedId,
            @Valid @RequestBody FeedUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        FeedResponseDto feed = feedService.updateFeed(feedId, request,
                userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("인증 피드가 성공적으로 수정되었습니다.", feed));
    }

    /**
     * 인증 피드를 삭제합니다.
     * 
     * @param feedId      삭제할 인증 피드 ID
     * @param userDetails 인증된 사용자 정보
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{feedId}")
    @DeleteFeed
    public ResponseEntity<ApiResponseDto<Void>> deleteFeed(
            @Parameter(description = "인증 피드 ID") @PathVariable Long feedId,
            @AuthenticationPrincipal UserDetails userDetails) {

        feedService.deleteFeed(feedId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("인증 피드가 성공적으로 삭제되었습니다.", null));
    }

    /**
     * 키워드로 인증 피드를 검색합니다.
     * 
     * @param groupId 모임 ULID
     * @param keyword 검색 키워드
     * @param page    페이지 번호 (0부터 시작)
     * @param size    페이지 크기
     * @param sort    정렬 조건 (e.g. createdAt,desc)
     * @return 검색된 인증 피드 목록
     */
    @GetMapping("/groups/{groupId}/search")
    @SearchFeeds
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<FeedResponseDto>>>> searchFeeds(
            @Parameter(description = "모임 ID") @PathVariable String groupId,
            @Parameter(description = "검색 키워드") @RequestParam String keyword,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "정렬 기준 (e.g. createdAt,desc)") @RequestParam(required = false) String sort) {

        Pageable pageable = createPageable(page, size, sort);
        CommonPageDto<List<FeedResponseDto>> feeds = feedService
                .searchFeeds(groupId, keyword, pageable);
        return ResponseEntity.ok(ApiResponseDto.success(feeds));
    }

    /**
     * 정렬 조건에 따른 Pageable 객체 생성
     * 
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param sort 정렬 조건 문자열
     * @return 설정된 Pageable 객체
     */
    private Pageable createPageable(int page, int size, String sort) {
        if (sort != null && !sort.isEmpty()) {
            String[] sortParams = sort.split(",");
            if (sortParams.length == 2) {
                Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
                return PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
            }
        }
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
