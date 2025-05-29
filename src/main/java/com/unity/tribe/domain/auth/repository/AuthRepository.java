package com.unity.tribe.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unity.tribe.domain.auth.entity.AuthTokenEntity;

@Repository
public interface AuthRepository extends JpaRepository<AuthTokenEntity, String> {

    Optional<AuthTokenEntity> findByUserId(String userId);
}
