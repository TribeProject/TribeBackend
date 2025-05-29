package com.unity.tribe.domain.group.entity;

import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "group", schema = "tribe", catalog = "")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private GroupCategory category;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupEntity that = (GroupEntity) o;
        return Objects.equals(groupId, that.groupId)
                && Objects.equals(hostId, that.hostId)
                && Objects.equals(categoryId, that.categoryId)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description)
                && Objects.equals(thumbnail, that.thumbnail)
                && Objects.equals(groupType, that.groupType)
                && Objects.equals(meetingType, that.meetingType)
                && Objects.equals(locationAddress, that.locationAddress)
                && Objects.equals(locationLatitude, that.locationLatitude)
                && Objects.equals(locationLongitude, that.locationLongitude)
                && Objects.equals(participants, that.participants)
                && Objects.equals(genderRestriction, that.genderRestriction)
                && Objects.equals(minAge, that.minAge)
                && Objects.equals(maxAge, that.maxAge)
                && Objects.equals(status, that.status)
                && Objects.equals(createdAt, that.createdAt)
                && Objects.equals(updatedAt, that.updatedAt)
                && Objects.equals(expiredAt, that.expiredAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, hostId, categoryId, title, description, thumbnail,
                groupType, meetingType, locationAddress, locationLatitude,
                locationLongitude, participants, genderRestriction, minAge,
                maxAge, status, createdAt, updatedAt, expiredAt);
    }
}