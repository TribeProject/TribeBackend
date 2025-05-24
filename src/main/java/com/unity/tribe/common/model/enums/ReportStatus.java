package com.unity.tribe.common.model.enums;

public enum ReportStatus {
    PENDING("PENDING", "처리 대기중"),
    RESOLVED("RESOLVED", "처리 완료"),
    REJECTED("REJECTED", "처리 거절");

    private final String code;
    private final String description;

    ReportStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}