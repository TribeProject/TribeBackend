package com.unity.tribe.common.util;

import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.*;

public class QueryUtil {
    public static BooleanExpression equalsIfHasText(StringPath path, String value) {
            return StringUtils.hasText(value)
                    ? path.eq(value)
                    : alwaysTrue();
    }

    public static BooleanExpression likeIfHasText(StringPath path, String value) {
        return StringUtils.hasText(value)
                ? path.like("%" + value + "%")
                : alwaysTrue();
    }

    public static BooleanExpression betweenIfNotNull(DateTimePath<LocalDateTime> path,
                                                     LocalDateTime startDate,
                                                     LocalDateTime endDate) {
        if (startDate != null && endDate != null) {
            return path.between(startDate, endDate);
        } else if (startDate != null) {
            return path.goe(startDate);
        } else if (endDate != null) {
            return path.loe(endDate);
        } else {
            return alwaysTrue();
        }
    }

    public static BooleanExpression booleanEqIfNotNull(BooleanPath path, Boolean value) {
        return value != null ? path.eq(value) : alwaysTrue();
    }

    private static BooleanExpression alwaysTrue() {
        return Expressions.asBoolean(true).isTrue();
    }

    public static BooleanExpression alwaysFalse() {
        return Expressions.asBoolean(false).isTrue();
    }
}
