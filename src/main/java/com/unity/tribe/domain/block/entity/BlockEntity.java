package com.unity.tribe.domain.block.entity;

import java.time.LocalDateTime;

import com.unity.tribe.common.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blocks", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "blocked_user_id" }))
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlockEntity extends BaseEntity {

    public enum BlockStatus {
        ACTIVE, INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Long blockId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "blocked_user_id", nullable = false)
    private String blockedUserId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BlockStatus status;

    @Column(name = "expired_at")
    private LocalDateTime expiredAt;

    public boolean isBlocking(String targetUserId) {
        return this.blockedUserId.equals(targetUserId) && this.status == BlockStatus.ACTIVE;
    }
}