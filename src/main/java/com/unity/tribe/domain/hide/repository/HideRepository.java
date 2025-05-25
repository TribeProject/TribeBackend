package com.unity.tribe.domain.hide.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unity.tribe.domain.hide.entity.HideEntity;
import com.unity.tribe.domain.hide.entity.HideEntity.TargetType;

@Repository
public interface HideRepository extends JpaRepository<HideEntity, Long> {

    // 특정 사용자의 숨김 목록 조회
    Page<HideEntity> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    // 특정 타입의 숨김 목록 조회
    Page<HideEntity> findByUserIdAndTargetTypeOrderByCreatedAtDesc(String userId, TargetType targetType,
            Pageable pageable);

    // 숨김 여부 확인
    boolean existsByUserIdAndTargetTypeAndTargetId(String userId, TargetType targetType, String targetId);
}