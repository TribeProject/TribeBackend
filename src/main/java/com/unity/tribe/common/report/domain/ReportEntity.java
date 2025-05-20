package com.unity.tribe.common.report.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "report", schema = "tribe", catalog = "")
public class ReportEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "report_id")
    private int reportId;
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "report_type")
    private String reportType;
    @Basic
    @Column(name = "target_feed_id")
    private String targetFeedId;
    @Basic
    @Column(name = "target_user_id")
    private String targetUserId;
    @Basic
    @Column(name = "target_comment_id")
    private Integer targetCommentId;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getTargetFeedId() {
        return targetFeedId;
    }

    public void setTargetFeedId(String targetFeedId) {
        this.targetFeedId = targetFeedId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public Integer getTargetCommentId() {
        return targetCommentId;
    }

    public void setTargetCommentId(Integer targetCommentId) {
        this.targetCommentId = targetCommentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportEntity that = (ReportEntity) o;
        return reportId == that.reportId && Objects.equals(userId, that.userId) && Objects.equals(reportType, that.reportType) && Objects.equals(targetFeedId, that.targetFeedId) && Objects.equals(targetUserId, that.targetUserId) && Objects.equals(targetCommentId, that.targetCommentId) && Objects.equals(type, that.type) && Objects.equals(status, that.status) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, userId, reportType, targetFeedId, targetUserId, targetCommentId, type, status, createdAt);
    }
}
