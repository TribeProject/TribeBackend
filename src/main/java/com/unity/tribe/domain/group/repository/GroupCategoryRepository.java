package com.unity.tribe.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unity.tribe.domain.group.entity.GroupCategory;

public interface GroupCategoryRepository extends JpaRepository<GroupCategory, Long> {
}