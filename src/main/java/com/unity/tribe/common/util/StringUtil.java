package com.unity.tribe.common.util;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtil {

    public static String maskString(String targetString, int index) {
        if(!StringUtils.hasText(targetString) || targetString.length() < index) {
            return targetString;
        }

        StringBuilder maskedUserId = new StringBuilder(targetString);
        for (int i = index - 1; i < targetString.length(); i++) {
            maskedUserId.setCharAt(i, '*'); // 3번째 자리부터 '*'로 대체
        }

        return maskedUserId.toString();
    }

    public static String ObjectToString(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
