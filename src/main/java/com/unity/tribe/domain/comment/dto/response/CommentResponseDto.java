package com.unity.tribe.domain.comment.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.unity.tribe.domain.comment.entity.CommentEntity.CommentStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String feedId;
    private UserSummaryDto user;
    private String content;
    private Long parentCommentId;
    private Integer depth;
    private CommentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<CommentResponseDto> replies;

    @Getter
    @Builder
    public static class UserSummaryDto {
        private String userId;
        private String nickname;
        private String profileImage;
    }
}