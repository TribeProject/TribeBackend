package com.unity.tribe.domain.group.entity;

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
@Table(name = "goal", schema = "tribe", catalog = "")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalEntity {

    public enum GoalStatus {
        ACTIVE, COMPLETED, FAILED
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "goal_id")
    private Long goalId;

    @Basic
    @Column(name = "group_id", nullable = false)
    private String groupId;

    @Basic
    @Column(name = "certification_rule_id")
    private Integer certificationRuleId;

    @Basic
    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "target_value", nullable = false)
    private Integer targetValue;

    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GoalStatus status;

    @Basic
    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Basic
    @Column(name = "end_date")
    private Timestamp endDate;

    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoalEntity that = (GoalEntity) o;
        return Objects.equals(goalId, that.goalId)
                && Objects.equals(groupId, that.groupId)
                && Objects.equals(certificationRuleId, that.certificationRuleId)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description)
                && Objects.equals(targetValue, that.targetValue)
                && Objects.equals(status, that.status)
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(endDate, that.endDate)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goalId, groupId, certificationRuleId, title, description,
                targetValue, status, startDate, endDate,
                createdAt, updatedAt);
    }
}