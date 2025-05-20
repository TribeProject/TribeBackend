package com.unity.tribe.common.certification.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "certification_rule", schema = "tribe", catalog = "")
public class CertificationRuleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "certification_rule_id")
    private int certificationRuleId;
    @Basic
    @Column(name = "group_id")
    private String groupId;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "goal_date")
    private Timestamp goalDate;
    @Basic
    @Column(name = "image_required")
    private byte imageRequired;
    @Basic
    @Column(name = "text_required")
    private byte textRequired;
    @Basic
    @Column(name = "certification_frequency")
    private String certificationFrequency;
    @Basic
    @Column(name = "weekly_count")
    private Integer weeklyCount;
    @Basic
    @Column(name = "weekly_days")
    private String weeklyDays;
    @Basic
    @Column(name = "monthly_days")
    private String monthlyDays;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public int getCertificationRuleId() {
        return certificationRuleId;
    }

    public void setCertificationRuleId(int certificationRuleId) {
        this.certificationRuleId = certificationRuleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(Timestamp goalDate) {
        this.goalDate = goalDate;
    }

    public byte getImageRequired() {
        return imageRequired;
    }

    public void setImageRequired(byte imageRequired) {
        this.imageRequired = imageRequired;
    }

    public byte getTextRequired() {
        return textRequired;
    }

    public void setTextRequired(byte textRequired) {
        this.textRequired = textRequired;
    }

    public String getCertificationFrequency() {
        return certificationFrequency;
    }

    public void setCertificationFrequency(String certificationFrequency) {
        this.certificationFrequency = certificationFrequency;
    }

    public Integer getWeeklyCount() {
        return weeklyCount;
    }

    public void setWeeklyCount(Integer weeklyCount) {
        this.weeklyCount = weeklyCount;
    }

    public String getWeeklyDays() {
        return weeklyDays;
    }

    public void setWeeklyDays(String weeklyDays) {
        this.weeklyDays = weeklyDays;
    }

    public String getMonthlyDays() {
        return monthlyDays;
    }

    public void setMonthlyDays(String monthlyDays) {
        this.monthlyDays = monthlyDays;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CertificationRuleEntity that = (CertificationRuleEntity) o;
        return certificationRuleId == that.certificationRuleId && imageRequired == that.imageRequired && textRequired == that.textRequired && Objects.equals(groupId, that.groupId) && Objects.equals(content, that.content) && Objects.equals(goalDate, that.goalDate) && Objects.equals(certificationFrequency, that.certificationFrequency) && Objects.equals(weeklyCount, that.weeklyCount) && Objects.equals(weeklyDays, that.weeklyDays) && Objects.equals(monthlyDays, that.monthlyDays) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(certificationRuleId, groupId, content, goalDate, imageRequired, textRequired, certificationFrequency, weeklyCount, weeklyDays, monthlyDays, createdAt, updatedAt);
    }
}
