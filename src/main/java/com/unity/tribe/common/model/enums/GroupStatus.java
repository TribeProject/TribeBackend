package com.unity.tribe.common.model.enums;

public enum GroupStatus {
    WAITING("W", "대기중"),
    ONGOING("O", "진행중"),
    FINISHED("F", "종료"),
    DISBANDED("D", "해산");

    private final String code;
    private final String description;

    GroupStatus(String code, String description) {
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