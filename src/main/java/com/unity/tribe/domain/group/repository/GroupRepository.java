package com.unity.tribe.domain.group.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.unity.tribe.common.model.enums.GroupStatus;
import com.unity.tribe.domain.group.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Page<Group> findAllByStatus(GroupStatus status, Pageable pageable);

    Page<Group> findAllByCategoryIdAndStatus(Long categoryId, GroupStatus status, Pageable pageable);

    @Query("SELECT g FROM Group g WHERE g.status = :status AND (g.name LIKE %:keyword% OR g.description LIKE %:keyword%)")
    Page<Group> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}