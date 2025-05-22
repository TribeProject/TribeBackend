package com.unity.tribe.domain.user.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "user", schema = "tribe", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public byte getTermsAgreed() {
        return termsAgreed;
    }

    public void setTermsAgreed(byte termsAgreed) {
        this.termsAgreed = termsAgreed;
    }

    public byte getPrivacyAgreed() {
        return privacyAgreed;
    }

    public void setPrivacyAgreed(byte privacyAgreed) {
        this.privacyAgreed = privacyAgreed;
    }

    public byte getMarketingAgreed() {
        return marketingAgreed;
    }

    public void setMarketingAgreed(byte marketingAgreed) {
        this.marketingAgreed = marketingAgreed;
    }

    public String getCiValue() {
        return ciValue;
    }

    public void setCiValue(String ciValue) {
        this.ciValue = ciValue;
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

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { 
            return true; 
        }
        if (o == null || getClass() != o.getClass()) {
            return false; 
        }
        UserEntity that = (UserEntity) o;
        return termsAgreed == that.termsAgreed && privacyAgreed == that.privacyAgreed && marketingAgreed == that.marketingAgreed && Objects.equals(userId, that.userId) && Objects.equals(name, that.name) && Objects.equals(joinType, that.joinType) && Objects.equals(email, that.email) && Objects.equals(passwordHash, that.passwordHash) && Objects.equals(nickname, that.nickname) && Objects.equals(gender, that.gender) && Objects.equals(birthDate, that.birthDate) && Objects.equals(profileImg, that.profileImg) && Objects.equals(ciValue, that.ciValue) && Objects.equals(status, that.status) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt) && Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, joinType, email, passwordHash, nickname, gender, birthDate, profileImg, termsAgreed, privacyAgreed, marketingAgreed, ciValue, status, createdAt, updatedAt, deletedAt);
    }
}
