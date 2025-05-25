package com.unity.tribe.domain.hide.entity;

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
@Table(name = "hides", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "target_type", "target_id" }))
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HideEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hide_id")
    private Long hideId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false)
    private TargetType targetType;

    @Column(name = "target_id", nullable = false)
    private String targetId;

    public enum TargetType {
        FEED, COMMENT
    }

    public boolean isFeedHide() {
        return this.targetType == TargetType.FEED;
    }

    public boolean isCommentHide() {
        return this.targetType == TargetType.COMMENT;
    }
}