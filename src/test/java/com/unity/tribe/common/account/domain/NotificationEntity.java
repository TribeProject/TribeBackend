package com.unity.tribe.common.account.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "notification", schema = "tribe", catalog = "")
public class NotificationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "notification_id")
    private int notificationId;
    @Basic
    @Column(name = "sender_id")
    private String senderId;
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "group_id")
    private String groupId;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "link_url")
    private String linkUrl;
    @Basic
    @Column(name = "is_read")
    private byte isRead;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public byte getIsRead() {
        return isRead;
    }

    public void setIsRead(byte isRead) {
        this.isRead = isRead;
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
        NotificationEntity that = (NotificationEntity) o;
        return notificationId == that.notificationId && isRead == that.isRead && Objects.equals(senderId, that.senderId) && Objects.equals(userId, that.userId) && Objects.equals(groupId, that.groupId) && Objects.equals(type, that.type) && Objects.equals(content, that.content) && Objects.equals(linkUrl, that.linkUrl) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId, senderId, userId, groupId, type, content, linkUrl, isRead, createdAt);
    }
}
