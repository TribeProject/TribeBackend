package com.unity.tribe.domain.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unity.tribe.domain.group.entity.Goal;
import com.unity.tribe.domain.group.entity.Group;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByGroup(Group group);
}