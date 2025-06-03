package com.unity.tribe.domain.banner.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "banner", schema = "tribe", catalog = "")
public class BannerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "banner_id")
    private int bannerId;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "image_pc")
    private String imagePc;
    @Basic
    @Column(name = "image_app")
    private String imageApp;
    @Basic
    @Column(name = "banner_device_type")
    private String bannerDeviceType;
    @Basic
    @Column(name = "link_url")
    private String linkUrl;
    @Basic
    @Column(name = "priority")
    private int priority;
    @Basic
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Basic
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "creator")
    private String creator;
    @Basic
    @Column(name = "updater")
    private String  updater;
    @Basic
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Basic
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BannerEntity that = (BannerEntity) o;
        return bannerId == that.bannerId && priority == that.priority && Objects.equals(title, that.title) && Objects.equals(imagePc, that.imagePc) && Objects.equals(imageApp, that.imageApp) && Objects.equals(bannerDeviceType, that.bannerDeviceType) && Objects.equals(linkUrl, that.linkUrl) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(status, that.status) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bannerId, title, imagePc, imageApp, bannerDeviceType, linkUrl, priority, startDate, endDate, status, createdAt, updatedAt);
    }
}
