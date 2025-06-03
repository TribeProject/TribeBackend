package com.unity.tribe.domain.user.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user", schema = "tribe", catalog = "")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "join_type")
    private String joinType;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password_hash")
    private String passwordHash;
    @Basic
    @Column(name = "nickname")
    private String nickname;
    @Basic
    @Column(name = "gender")
    private String gender;
    @Basic
    @Column(name = "role")
    private String role;
    @Basic
    @Column(name = "birth_date")
    private Date birthDate;
    @Basic
    @Column(name = "profile_img")
    private String profileImg;
    @Basic
    @Column(name = "terms_agreed")
    private byte termsAgreed;
    @Basic
    @Column(name = "privacy_agreed")
    private byte privacyAgreed;
    @Basic
    @Column(name = "marketing_agreed")
    private byte marketingAgreed;
    @Basic
    @Column(name = "ci_value")
    private String ciValue;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Basic
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Basic
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return termsAgreed == that.termsAgreed && privacyAgreed == that.privacyAgreed
                && marketingAgreed == that.marketingAgreed && Objects.equals(userId, that.userId)
                && Objects.equals(name, that.name) && Objects.equals(joinType, that.joinType)
                && Objects.equals(email, that.email) && Objects.equals(passwordHash, that.passwordHash)
                && Objects.equals(nickname, that.nickname) && Objects.equals(gender, that.gender)
                && Objects.equals(birthDate, that.birthDate) && Objects.equals(profileImg, that.profileImg)
                && Objects.equals(ciValue, that.ciValue) && Objects.equals(status, that.status)
                && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt)
                && Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, joinType, email, passwordHash, nickname, gender, birthDate, profileImg,
                termsAgreed, privacyAgreed, marketingAgreed, ciValue, status, createdAt, updatedAt, deletedAt);
    }
}
