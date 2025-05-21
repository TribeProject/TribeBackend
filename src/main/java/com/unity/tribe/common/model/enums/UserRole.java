package com.unity.tribe.common.model.enums;

public enum UserRole {

    USER("U", "USER"),
    ADMIN("A", "ADMIN");

    private final String code;
    private final String description;

    UserRole(String code, String description) {
        this.code = code;
        this.description = description;
    }


}
