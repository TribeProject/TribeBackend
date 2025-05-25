package com.unity.tribe.domain.feed.entity;

import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feed", schema = "tribe", catalog = "")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedEntity {

    public enum FeedStatus {
        ACTIVE, DELETED
    }

    public enum FeedType {
        NORMAL, CERTIFICATION
    }

    public enum FeedFrequency {
        DAILY, WEEKLY, MONTHLY
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "feed_id")
    private Long feedId;

    @Basic
    @Column(name = "group_id", nullable = false)
    private String groupId;

    @Basic
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Basic
    @Column(name = "feed_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedType feedType;

    @Basic
    @Column(name = "image")
    private String image;

    @Basic
    @Column(name = "content_text")
    private String contentText;

    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedStatus status;

    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FeedEntity that = (FeedEntity) o;
        return Objects.equals(feedId, that.feedId) &&
                Objects.equals(groupId, that.groupId) &&
                Objects.equals(userId, that.userId) &&
                feedType == that.feedType &&
                Objects.equals(image, that.image) &&
                Objects.equals(contentText, that.contentText) &&
                status == that.status &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedId, groupId, userId, feedType, image, contentText, status, createdAt, updatedAt);
    }
}