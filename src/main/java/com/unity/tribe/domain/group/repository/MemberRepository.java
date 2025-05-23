package com.unity.tribe.domain.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unity.tribe.domain.group.entity.Group;
import com.unity.tribe.domain.group.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByGroup(Group group);

    Optional<Member> findByGroupAndUserId(Group group, String userId);
}