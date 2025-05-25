package com.unity.tribe.domain.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unity.tribe.domain.comment.entity.CommentEntity;
import com.unity.tribe.domain.comment.entity.CommentEntity.CommentStatus;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // 특정 피드의 댓글 목록 조회 (최상위 댓글만)
    @Query("SELECT c FROM CommentEntity c WHERE c.feedId = :feedId AND c.parentCommentId IS NULL AND c.status = :status ORDER BY c.createdAt ASC")
    Page<CommentEntity> findTopLevelCommentsByFeedId(@Param("feedId") String feedId,
            @Param("status") CommentStatus status,
            Pageable pageable);

    // 특정 부모 댓글의 대댓글 목록 조회
    @Query("SELECT c FROM CommentEntity c WHERE c.parentCommentId = :parentCommentId AND c.status = :status ORDER BY c.createdAt ASC")
    List<CommentEntity> findRepliesByParentCommentId(@Param("parentCommentId") Long parentCommentId,
            @Param("status") CommentStatus status);

    // 특정 피드의 모든 댓글 수 조회
    long countByFeedIdAndStatus(String feedId, CommentStatus status);

    // 특정 사용자의 댓글 목록 조회
    Page<CommentEntity> findByUserIdAndStatusOrderByCreatedAtDesc(String userId, CommentStatus status,
            Pageable pageable);

    // 댓글 ID와 작성자로 댓글 조회 (권한 확인용)
    Optional<CommentEntity> findByCommentIdAndUserId(Long commentId, String userId);

    // 특정 피드의 댓글과 대댓글을 모두 조회
    @Query("SELECT c FROM CommentEntity c WHERE c.feedId = :feedId AND c.status = :status ORDER BY " +
            "CASE WHEN c.parentCommentId IS NULL THEN c.commentId ELSE c.parentCommentId END, " +
            "c.parentCommentId NULLS FIRST, c.createdAt ASC")
    List<CommentEntity> findAllCommentsByFeedIdWithHierarchy(@Param("feedId") String feedId,
            @Param("status") CommentStatus status);
}