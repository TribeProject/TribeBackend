package com.unity.tribe.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unity.tribe.domain.report.entity.ReportEntity;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
}