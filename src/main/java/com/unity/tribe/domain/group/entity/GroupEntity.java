package com.unity.tribe.domain.group.entity;

import java.sql.Timestamp;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "group", schema = "tribe", catalog = "")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GroupEntity {

    public enum GroupStatus {
        WAITING, ONGOING, FINISHED, DISBANDED
    }

    public enum GroupType {
        MISSION, CONTINUOUS
    }

    public enum GenderRestriction {
        NONE, MALE, FEMALE
    }

    @Id
    @Column(name = "group_id")
    private String groupId;

    @Basic
    @Column(name = "host_id", nullable = false)
    private String hostId;

    @Basic
    @Column(name = "category_code", nullable = false)
    private String categoryCode;

    @Basic
    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "thumbnail")
    private String thumbnail;

    @Basic
    @Column(name = "group_type")
    private String groupType;

    @Basic
    @Column(name = "meeting_type")
    private String meetingType;

    @Basic
    @Column(name = "location_address")
    private String locationAddress;

    @Basic
    @Column(name = "location_latitude")
    private String locationLatitude;

    @Basic
    @Column(name = "location_longitude")
    private String locationLongitude;

    @Basic
    @Column(name = "participants", nullable = false)
    private Integer participants;

    @Basic
    @Column(name = "gender_restriction")
    private String genderRestriction;

    @Basic
    @Column(name = "min_age")
    private Integer minAge;

    @Basic
    @Column(name = "max_age")
    private Integer maxAge;

    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupStatus status;

    @Basic
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Basic
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Basic
    @Column(name = "expired_at")
    private Timestamp expiredAt;
}