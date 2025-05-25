package com.unity.tribe.domain.feed.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unity.tribe.domain.feed.entity.FeedEntity;
import com.unity.tribe.domain.feed.entity.FeedEntity.FeedStatus;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {

    Page<FeedEntity> findAllByGroupId(String groupId, Pageable pageable);

    Page<FeedEntity> findAllByUserId(String userId, Pageable pageable);

    Page<FeedEntity> findAllByGroupIdAndStatus(String groupId, FeedStatus status, Pageable pageable);

    @Query("SELECT f FROM FeedEntity f WHERE f.groupId = :groupId AND (f.contentText LIKE %:keyword%)")
    Page<FeedEntity> searchByKeyword(@Param("groupId") String groupId, @Param("keyword") String keyword,
            Pageable pageable);
}