package com.unity.tribe.domain.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.domain.report.dto.ReportCreateRequestDto;
import com.unity.tribe.domain.report.entity.ReportEntity;
import com.unity.tribe.domain.report.repository.ReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public void createReport(ReportCreateRequestDto request, String reporterUserId) {
        ReportEntity report = ReportEntity.builder()
                .userId(reporterUserId)
                .type(request.getTargetType())
                .targetFeedId(request.getTargetFeedId())
                .targetUserId(request.getTargetUserId())
                .targetCommentId(Integer.valueOf(request.getTargetCommentId()))
                .reportType(request.getReasonType())
                .build();

        reportRepository.save(report);
    }
}