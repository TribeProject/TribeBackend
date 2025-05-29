package com.unity.tribe.domain.comment.controller;

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
import com.unity.tribe.domain.comment.docs.CommentApi;
import com.unity.tribe.domain.comment.docs.CreateComment;
import com.unity.tribe.domain.comment.docs.DeleteComment;
import com.unity.tribe.domain.comment.docs.GetComments;
import com.unity.tribe.domain.comment.docs.UpdateComment;
import com.unity.tribe.domain.comment.dto.request.CommentCreateRequestDto;
import com.unity.tribe.domain.comment.dto.request.CommentUpdateRequestDto;
import com.unity.tribe.domain.comment.dto.response.CommentResponseDto;
import com.unity.tribe.domain.comment.service.CommentService;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * 댓글 관련 컨트롤러
 */
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@CommentApi
public class CommentController {

    private final CommentService commentService;

    /**
     * 특정 피드의 댓글 목록을 조회합니다.
     * 
     * @param feedId 피드 ID
     * @param page   페이지 번호 (0부터 시작)
     * @param size   페이지 크기
     * @return 페이징된 댓글 목록
     */
    @GetMapping("/feeds/{feedId}")
    @GetComments
    public ResponseEntity<ApiResponseDto<CommonPageDto<List<CommentResponseDto>>>> getComments(
            @Parameter(description = "피드 ID") @PathVariable String feedId,
            @Parameter(description = "페이지 번호 (0부터 시작)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기") @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        CommonPageDto<List<CommentResponseDto>> comments = commentService.getComments(feedId, pageable);
        return ResponseEntity.ok(ApiResponseDto.success(comments));
    }

    /**
     * 새로운 댓글을 생성합니다.
     * 
     * @param feedId      피드 ID
     * @param request     댓글 생성 요청 데이터
     * @param userDetails 인증된 사용자 정보
     * @return 생성된 댓글 정보
     */
    @PostMapping("/feeds/{feedId}")
    @CreateComment
    public ResponseEntity<ApiResponseDto<CommentResponseDto>> createComment(
            @Parameter(description = "피드 ID") @PathVariable String feedId,
            @Valid @RequestBody CommentCreateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        CommentResponseDto comment = commentService.createComment(feedId, request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("댓글이 성공적으로 생성되었습니다.", comment));
    }

    /**
     * 기존 댓글을 수정합니다.
     * 
     * @param commentId   수정할 댓글 ID
     * @param request     수정할 댓글 정보
     * @param userDetails 인증된 사용자 정보
     * @return 수정된 댓글 정보
     */
    @PutMapping("/{commentId}")
    @UpdateComment
    public ResponseEntity<ApiResponseDto<CommentResponseDto>> updateComment(
            @Parameter(description = "댓글 ID") @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequestDto request,
            @AuthenticationPrincipal UserDetails userDetails) {

        CommentResponseDto comment = commentService.updateComment(commentId, request, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("댓글이 성공적으로 수정되었습니다.", comment));
    }

    /**
     * 댓글을 삭제합니다.
     * 
     * @param commentId   삭제할 댓글 ID
     * @param userDetails 인증된 사용자 정보
     * @return 삭제 성공 메시지
     */
    @DeleteMapping("/{commentId}")
    @DeleteComment
    public ResponseEntity<ApiResponseDto<Void>> deleteComment(
            @Parameter(description = "댓글 ID") @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        commentService.deleteComment(commentId, userDetails.getUsername());
        return ResponseEntity.ok(ApiResponseDto.success("댓글이 성공적으로 삭제되었습니다.", null));
    }
}