package com.unity.tribe.domain.group.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unity.tribe.domain.group.entity.GroupCategory;
import com.unity.tribe.domain.group.entity.GroupCategory.GroupCategoryCode;

public interface GroupCategoryRepository extends JpaRepository<GroupCategory, Long> {

    Optional<GroupCategory> findByCode(GroupCategoryCode code);
}