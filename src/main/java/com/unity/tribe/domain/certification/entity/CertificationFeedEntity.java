package com.unity.tribe.domain.certification.entity;

import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "certification_feed", schema = "tribe", catalog = "")
public class CertificationFeedEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "feed_id")
    private String feedId;
    @Basic
    @Column(name = "group_id")
    private String groupId;
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "feed_type")
    private String feedType;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "content_text")
    private String contentText;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { 
            return true; 
        }
        if (o == null || getClass() != o.getClass()) {
            return false; 
        }
        CertificationFeedEntity that = (CertificationFeedEntity) o;
        return Objects.equals(feedId, that.feedId) && Objects.equals(groupId, that.groupId) && Objects.equals(userId, that.userId) && Objects.equals(feedType, that.feedType) && Objects.equals(image, that.image) && Objects.equals(contentText, that.contentText) && Objects.equals(status, that.status) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedId, groupId, userId, feedType, image, contentText, status, createdAt, updatedAt);
    }
}
