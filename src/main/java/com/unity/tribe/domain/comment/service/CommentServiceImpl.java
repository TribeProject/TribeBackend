package com.unity.tribe.domain.comment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeApiException;
import com.unity.tribe.common.model.CommonPageDto;
import com.unity.tribe.domain.comment.dto.request.CommentCreateRequestDto;
import com.unity.tribe.domain.comment.dto.request.CommentUpdateRequestDto;
import com.unity.tribe.domain.comment.dto.response.CommentResponseDto;
import com.unity.tribe.domain.comment.entity.CommentEntity;
import com.unity.tribe.domain.comment.entity.CommentEntity.CommentStatus;
import com.unity.tribe.domain.comment.repository.CommentRepository;
import com.unity.tribe.domain.feed.entity.FeedEntity;
import com.unity.tribe.domain.feed.repository.FeedRepository;
import com.unity.tribe.domain.user.entity.UserEntity;
import com.unity.tribe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<CommentResponseDto>> getComments(String feedId, Pageable pageable) {
        // 피드 존재 확인
        FeedEntity feed = feedRepository.findById(Long.valueOf(feedId))
                .orElseThrow(() -> new TribeApiException(ErrorCode.FEED_NOT_FOUND_FOR_COMMENT));

        // 최상위 댓글 조회
        Page<CommentEntity> commentPage = commentRepository.findTopLevelCommentsByFeedId(feedId,
                CommentStatus.ACTIVE, pageable);

        List<CommentResponseDto> commentDtos = commentPage.getContent().stream()
                .map(this::convertToResponseDtoWithReplies)
                .collect(Collectors.toList());

        return CommonPageDto.<List<CommentResponseDto>>builder()
                .data(commentDtos)
                .totalElements(commentPage.getTotalElements())
                .totalPages(commentPage.getTotalPages())
                .page(commentPage.getNumber())
                .size(commentPage.getSize())
                .build();
    }

    @Override
    public CommentResponseDto createComment(String feedId, CommentCreateRequestDto request, String userId) {
        // 사용자 존재 확인
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.USER_NOT_FOUND));

        // 피드 존재 확인
        FeedEntity feed = feedRepository.findById(Long.valueOf(feedId))
                .orElseThrow(() -> new TribeApiException(ErrorCode.FEED_NOT_FOUND_FOR_COMMENT));

        // 댓글 내용 검증
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new TribeApiException(ErrorCode.COMMENT_EMPTY);
        }

        // 부모 댓글 확인 (대댓글인 경우)
        Integer depth = 0;
        if (request.getParentCommentId() != null) {
            CommentEntity parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new TribeApiException(ErrorCode.PARENT_COMMENT_NOT_FOUND));

            // 부모 댓글이 활성 상태인지 확인
            if (parentComment.getStatus() != CommentStatus.ACTIVE) {
                throw new TribeApiException(ErrorCode.PARENT_COMMENT_NOT_FOUND);
            }

            // 대댓글의 깊이는 최대 1단계까지만 허용
            if (parentComment.getDepth() >= 1) {
                throw new TribeApiException(ErrorCode.INVALID_COMMENT_DEPTH);
            }

            depth = parentComment.getDepth() + 1;
        }

        // 댓글 엔티티 생성
        CommentEntity commentEntity = CommentEntity.builder()
                .userId(userId)
                .feedId(feedId)
                .parentCommentId(request.getParentCommentId())
                .content(request.getContent().trim())
                .depth(depth)
                .status(CommentStatus.ACTIVE)
                .build();

        CommentEntity savedComment = commentRepository.save(commentEntity);
        return convertToResponseDto(savedComment);
    }

    @Override
    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto request, String userId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.COMMENT_NOT_FOUND));

        // 작성자 확인
        if (!commentEntity.getUserId().equals(userId)) {
            throw new TribeApiException(ErrorCode.COMMENT_ACCESS_DENIED);
        }

        // 삭제된 댓글인지 확인
        if (commentEntity.getStatus() == CommentStatus.DELETED) {
            throw new TribeApiException(ErrorCode.COMMENT_ALREADY_DELETED);
        }

        // 댓글 내용 검증
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new TribeApiException(ErrorCode.COMMENT_EMPTY);
        }

        // 댓글 업데이트
        commentEntity.updateContent(request.getContent().trim());
        CommentEntity updatedComment = commentRepository.save(commentEntity);

        return convertToResponseDto(updatedComment);
    }

    @Override
    public void deleteComment(Long commentId, String userId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.COMMENT_NOT_FOUND));

        // 작성자 확인
        if (!commentEntity.getUserId().equals(userId)) {
            throw new TribeApiException(ErrorCode.COMMENT_ACCESS_DENIED);
        }

        // 이미 삭제된 댓글인지 확인
        if (commentEntity.getStatus() == CommentStatus.DELETED) {
            throw new TribeApiException(ErrorCode.COMMENT_ALREADY_DELETED);
        }

        // 소프트 삭제
        commentEntity.delete();
        commentRepository.save(commentEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonPageDto<List<CommentResponseDto>> getUserComments(String userId, Pageable pageable) {
        // 사용자 존재 확인
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new TribeApiException(ErrorCode.USER_NOT_FOUND));

        Page<CommentEntity> commentPage = commentRepository.findByUserIdAndStatusOrderByCreatedAtDesc(
                userId, CommentStatus.ACTIVE, pageable);

        List<CommentResponseDto> commentDtos = commentPage.getContent().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        return CommonPageDto.<List<CommentResponseDto>>builder()
                .data(commentDtos)
                .totalElements(commentPage.getTotalElements())
                .totalPages(commentPage.getTotalPages())
                .page(commentPage.getNumber())
                .size(commentPage.getSize())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public long getCommentCount(String feedId) {
        return commentRepository.countByFeedIdAndStatus(feedId, CommentStatus.ACTIVE);
    }

    private CommentResponseDto convertToResponseDto(CommentEntity entity) {
        // 사용자 정보 조회
        UserEntity user = userRepository.findById(entity.getUserId())
                .orElse(null);

        CommentResponseDto.UserSummaryDto userSummary = null;
        if (user != null) {
            userSummary = CommentResponseDto.UserSummaryDto.builder()
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .profileImage(user.getProfileImg())
                    .build();
        }

        return CommentResponseDto.builder()
                .commentId(entity.getCommentId())
                .feedId(entity.getFeedId())
                .user(userSummary)
                .content(entity.getContent())
                .parentCommentId(entity.getParentCommentId())
                .depth(entity.getDepth())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deletedAt(entity.getDeletedAt())
                .build();
    }

    private CommentResponseDto convertToResponseDtoWithReplies(CommentEntity entity) {
        CommentResponseDto commentDto = convertToResponseDto(entity);

        // 대댓글 조회
        List<CommentEntity> replies = commentRepository.findRepliesByParentCommentId(
                entity.getCommentId(), CommentStatus.ACTIVE);

        List<CommentResponseDto> replyDtos = replies.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());

        return CommentResponseDto.builder()
                .commentId(commentDto.getCommentId())
                .feedId(commentDto.getFeedId())
                .user(commentDto.getUser())
                .content(commentDto.getContent())
                .parentCommentId(commentDto.getParentCommentId())
                .depth(commentDto.getDepth())
                .status(commentDto.getStatus())
                .createdAt(commentDto.getCreatedAt())
                .updatedAt(commentDto.getUpdatedAt())
                .deletedAt(commentDto.getDeletedAt())
                .replies(replyDtos)
                .build();
    }
}