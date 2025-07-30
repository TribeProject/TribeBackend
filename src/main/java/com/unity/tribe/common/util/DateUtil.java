package com.unity.tribe.common.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtil {

    public static LocalDateTime getLocalDateTimeFromString(String stringDate) {
        if (!StringUtils.hasText(stringDate)) {
            throw new RuntimeException("문자열이 비어있습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(stringDate, formatter);
        return localDate.atStartOfDay();
    }

    public static String getLocalDateTimeToString(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getTodayLocalDateTimeToString(String pattern) {

        LocalDateTime localDateTime = LocalDateTime.now();
        return getLocalDateTimeToString(localDateTime, pattern);
    }

    public static LocalDate getLocalDateFromString(String stringDate) {
        if (!StringUtils.hasText(stringDate)) {
            throw new RuntimeException("문자열이 비어있습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(stringDate, formatter);

    }

    public static String getStringFromDate(Date date, String type) {
        SimpleDateFormat formatter = new SimpleDateFormat(type);
        return formatter.format(date);
    }
}
