package com.unity.tribe.common.account.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "block", schema = "tribe", catalog = "")
public class BlockEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "block_id")
    private int blockId;
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "target_user_id")
    private String targetUserId;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "expired_at")
    private Timestamp expiredAt;

    public int getBlockId() {
        return blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Timestamp expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockEntity that = (BlockEntity) o;
        return blockId == that.blockId && Objects.equals(userId, that.userId) && Objects.equals(targetUserId, that.targetUserId) && Objects.equals(createdAt, that.createdAt) && Objects.equals(status, that.status) && Objects.equals(expiredAt, that.expiredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockId, userId, targetUserId, createdAt, status, expiredAt);
    }
}
