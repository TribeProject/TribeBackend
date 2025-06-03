package com.unity.tribe.domain.report.entity;

import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "report", schema = "tribe", catalog = "")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportEntity {
    public enum ReportReasonType {
        PURE, VIOLENCE, COMMENT
    }

    public enum ReportTargetType {
        FEED, COMMENT
    }

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
    @Column(name = "target_group_id")
    private String targetGroupId;
    @Basic
    @Column(name = "target_feed_id")
    private String targetFeedId;
    @Basic
    @Column(name = "target_user_id")
    private String targetUserId;
    @Basic
    @Column(name = "target_comment_id")
    private int targetCommentId;
    @Basic
    @Column(name = "report_count")
    private int reportCount;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReportEntity that = (ReportEntity) o;
        return reportId == that.reportId && Objects.equals(userId, that.userId)
                && Objects.equals(reportType, that.reportType) && Objects.equals(targetFeedId, that.targetFeedId)
                && Objects.equals(targetUserId, that.targetUserId)
                && Objects.equals(targetCommentId, that.targetCommentId) && Objects.equals(type, that.type)
                && Objects.equals(status, that.status) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, userId, reportType, targetFeedId, targetUserId, targetCommentId, type, status,
                createdAt);
    }
}
