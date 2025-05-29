package com.unity.tribe.domain.block.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unity.tribe.domain.block.entity.BlockEntity;

@Repository
public interface BlockRepository extends JpaRepository<BlockEntity, Long> {

    // 특정 사용자의 차단 목록 조회
    Page<BlockEntity> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    // 차단 여부 확인
    boolean existsByUserIdAndBlockedUserId(String userId, String blockedUserId);

    // 특정 차단 조회
    Optional<BlockEntity> findByUserIdAndBlockedUserId(String userId, String blockedUserId);

    // 사용자가 차단한 사용자 ID 목록 조회
    @Query("SELECT b.blockedUserId FROM BlockEntity b WHERE b.userId = :userId")
    List<String> findBlockedUserIdsByUserId(@Param("userId") String userId);

    // 차단 개수 조회
    long countByUserId(String userId);

    // 서로 차단 관계 확인 (상호 차단)
    @Query("SELECT COUNT(b) > 0 FROM BlockEntity b WHERE " +
            "(b.userId = :userId1 AND b.blockedUserId = :userId2) OR " +
            "(b.userId = :userId2 AND b.blockedUserId = :userId1)")
    boolean existsMutualBlock(@Param("userId1") String userId1, @Param("userId2") String userId2);
}