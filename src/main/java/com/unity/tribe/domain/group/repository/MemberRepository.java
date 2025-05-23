package com.unity.tribe.domain.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unity.tribe.domain.member.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    List<MemberEntity> findAllByGroupId(String groupId);

    Optional<MemberEntity> findByGroupIdAndUserId(String groupId, String userId);
}