package com.unity.tribe.common.model.enums;

public enum ReportReasonType {

    AD("AD", "광고 및 홍보성 글"),
    OBSCENE("OBSCENE", "음란성/선정적인 글"),
    SPAM("SPAM", "도배성 내용의 글"),
    DEFAMATION("DEFAMATION", "명예훼손 및 타인을 비방하는 글"),
    POLITICAL("POLITICAL", "정치적·사회적 의견을 표출한 글");

    private final String code;
    private final String description;

    ReportReasonType(String code, String description) {
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