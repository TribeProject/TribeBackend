package com.unity.tribe.domain.report.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unity.tribe.common.exception.ErrorCode;
import com.unity.tribe.common.exception.TribeApiException;
import com.unity.tribe.domain.report.dto.ReportCreateRequestDto;
import com.unity.tribe.domain.report.entity.ReportEntity;
import com.unity.tribe.domain.report.repository.ReportRepository;
import com.unity.tribe.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Override
    public void createReport(ReportCreateRequestDto request, String reporterUserId) {
        // 신고자 검증
        if (!userRepository.existsById(reporterUserId)) {
            throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
        }

        // 신고 대상 검증
        if (request.getTargetUserId() != null) {
            if (!userRepository.existsById(request.getTargetUserId())) {
                throw new TribeApiException(ErrorCode.USER_NOT_FOUND);
            }

            // 자기 자신을 신고할 수 없음
            if (reporterUserId.equals(request.getTargetUserId())) {
                throw new TribeApiException(ErrorCode.CANNOT_REPORT_SELF);
            }
        }

        // 신고 엔티티 생성
        ReportEntity report = ReportEntity.builder()
                .userId(reporterUserId)
                .type(request.getTargetType())
                .targetFeedId(request.getTargetFeedId())
                .targetUserId(request.getTargetUserId())
                .targetCommentId(
                        request.getTargetCommentId() != null ? Integer.valueOf(request.getTargetCommentId()) : null)
                .reportType(request.getReasonType())
                .status("PENDING")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        reportRepository.save(report);
    }
}