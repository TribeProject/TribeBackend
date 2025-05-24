package com.unity.tribe.domain.report.service;

import com.unity.tribe.domain.report.dto.ReportCreateRequestDto;

public interface ReportService {
    void createReport(ReportCreateRequestDto request, String reporterUserId);
}