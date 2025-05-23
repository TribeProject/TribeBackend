package com.unity.tribe.domain.group.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unity.tribe.common.model.enums.GroupStatus;
import com.unity.tribe.domain.group.entity.GroupEntity;

public interface GroupRepository extends JpaRepository<GroupEntity, String> {

    Page<GroupEntity> findAllByStatus(GroupStatus status, Pageable pageable);

    Page<GroupEntity> findAllByCategoryIdAndStatus(Long categoryId, GroupStatus status, Pageable pageable);

    @Query("SELECT g FROM GroupEntity g WHERE g.status = :status AND (g.title LIKE %:keyword% OR g.description LIKE %:keyword%)")
    Page<GroupEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}