package com.unity.tribe.common.model.enums;

public enum GroupCategoryCode {
    FOOD("F", "FOOD"),
    TRIP("T", "TRIP"),
    EXERCISE_AND_ACTIVITE("EA", "EXERCISE_AND_ACTIVITE"),
    CULTURE_AND_ART("CA", "CULTURE_ART"),
    SELF_DEVELOPMENT("SD", "SELF_DEVELOPMENT"),
    GAME("G", "GAME"),
    BEAUTY_AND_FASHION("BF", "BEAUTY_AND_FASHION"),
    VOLUNTEER("V", "VOLUNTEER"),;

    private String code;
    private String description;

    GroupCategoryCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
