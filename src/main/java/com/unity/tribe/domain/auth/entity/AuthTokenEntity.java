package com.unity.tribe.domain.auth.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auth_token", schema = "tribe", catalog = "")
public class AuthTokenEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "auth_token_id")
    private String authTokenId;
    @Basic
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "access_token")
    private String accessToken;
    @Basic
    @Column(name = "refresh_token")
    private String refreshToken;
    @Basic
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @Basic
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Basic
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public String getAuthTokenId() {
        return authTokenId;
    }

    public void setAuthTokenId(String authTokenId) {
        this.authTokenId = authTokenId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthTokenEntity that = (AuthTokenEntity) o;
        return authTokenId == that.authTokenId && Objects.equals(userId, that.userId) && Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken) && Objects.equals(expiresAt, that.expiresAt) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authTokenId, userId, accessToken, refreshToken, expiresAt, createdAt, updatedAt);
    }
}
