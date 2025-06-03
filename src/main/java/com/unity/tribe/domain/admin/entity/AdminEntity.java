package com.unity.tribe.domain.admin.entity;


import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin", schema = "tribe", catalog = "")
public class AdminEntity {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Basic
    @Column(name = "admin_id")
    private String adminId;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password_hash")
    private String passwordHash;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "role")
    private String role;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "creator_id")
    private String creatorId;
    @Basic
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;
    @Basic
    @Column(name = "last_login_ip")
    private LocalDateTime lastLoginIp;
    @Basic
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Basic
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdminEntity that = (AdminEntity) o;
        return Objects.equals(userId, that.userId) && Objects.equals(email, that.email)
                && Objects.equals(passwordHash, that.passwordHash) && Objects.equals(name, that.name)
                && Objects.equals(role, that.role) && Objects.equals(status, that.status)
                && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, passwordHash, name, role, status, createdAt, updatedAt);
    }
}
