package com.unity.tribe.core.constant;

public enum UserRole {

    USER("USER", "USER"),
    ADMIN("ADMIN", "ADMIN");

    private final String code;
    private final String description;

    UserRole(String code, String description) {
        this.code = code;
        this.description = description;
    }


}
