package com.unity.tribe.common.model.enums;

public enum ReportTargetType {

    FEED("FEED", "인증 피드"),
    USER("USER", "사용자"),
    COMMENT("COMMENT", "댓글");

    private final String code;
    private final String description;

    ReportTargetType(String code, String description) {
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