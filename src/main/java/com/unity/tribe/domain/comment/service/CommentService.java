package com.unity.tribe.domain.comment.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.comment.dto.request.CommentCreateRequestDto;
import com.unity.tribe.domain.comment.dto.request.CommentUpdateRequestDto;
import com.unity.tribe.domain.comment.dto.response.CommentResponseDto;

public interface CommentService {

    /**
     * 피드의 댓글 목록을 조회합니다.
     * 
     * @param feedId   피드 ID
     * @param pageable 페이지 정보
     * @return 댓글 목록 페이지 정보 (대댓글 포함)
     */
    CommonPageDto<List<CommentResponseDto>> getComments(String feedId, Pageable pageable);

    /**
     * 댓글을 생성합니다.
     * 
     * @param feedId  피드 ID
     * @param request 댓글 생성 요청 정보
     * @param userId  사용자 ID
     * @return 생성된 댓글 정보
     */
    CommentResponseDto createComment(String feedId, CommentCreateRequestDto request, String userId);

    /**
     * 댓글을 수정합니다.
     * 
     * @param commentId 댓글 ID
     * @param request   댓글 수정 요청 정보
     * @param userId    사용자 ID
     * @return 수정된 댓글 정보
     */
    CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto request, String userId);

    /**
     * 댓글을 삭제합니다.
     * 
     * @param commentId 댓글 ID
     * @param userId    사용자 ID
     */
    void deleteComment(Long commentId, String userId);

    /**
     * 특정 사용자의 댓글 목록을 조회합니다.
     * 
     * @param userId   사용자 ID
     * @param pageable 페이지 정보
     * @return 사용자의 댓글 목록
     */
    CommonPageDto<List<CommentResponseDto>> getUserComments(String userId, Pageable pageable);

    /**
     * 피드의 댓글 수를 조회합니다.
     * 
     * @param feedId 피드 ID
     * @return 댓글 수
     */
    long getCommentCount(String feedId);
}