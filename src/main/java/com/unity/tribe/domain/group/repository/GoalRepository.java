package com.unity.tribe.domain.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unity.tribe.domain.group.entity.GoalEntity;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {
    List<GoalEntity> findAllByGroupId(String groupId);
}