package com.unity.tribe.common.account.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "hide", schema = "tribe", catalog = "")
public class HideEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "hide_id")
    private String hideId;
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "target_type")
    private String targetType;
    @Basic
    @Column(name = "target_comment_id")
    private Integer targetCommentId;
    @Basic
    @Column(name = "target_feed_id")
    private Integer targetFeedId;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    public String getHideId() {
        return hideId;
    }

    public void setHideId(String hideId) {
        this.hideId = hideId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Integer getTargetCommentId() {
        return targetCommentId;
    }

    public void setTargetCommentId(Integer targetCommentId) {
        this.targetCommentId = targetCommentId;
    }

    public Integer getTargetFeedId() {
        return targetFeedId;
    }

    public void setTargetFeedId(Integer targetFeedId) {
        this.targetFeedId = targetFeedId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HideEntity that = (HideEntity) o;
        return Objects.equals(hideId, that.hideId) && Objects.equals(userId, that.userId) && Objects.equals(targetType, that.targetType) && Objects.equals(targetCommentId, that.targetCommentId) && Objects.equals(targetFeedId, that.targetFeedId) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hideId, userId, targetType, targetCommentId, targetFeedId, createdAt);
    }
}
