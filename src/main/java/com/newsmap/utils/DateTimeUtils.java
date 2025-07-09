package com.newsmap.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class DateTimeUtils {
    public static final String DATE_TIME_SLASH_PATTERN = "yyyy/MM/dd";
    public static final String FULL_DATE_TIME_SLASH_PATTERN = "yyyy/MM/dd HH:mm:ss";
    public static final String FULL_DATE_TIME_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String FULL_DATE_TIME_PATTERN_TZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String FULL_DATE_TIME_PATTERN_MMDDYYYY = "MM-dd-yyyy HH:mm:ss";
    public static final String FULL_DATE_TIME_PATTERN_YYYYMMDD = "yyyy-MM-dd HH:mm:ss";
    public static final Instant MIN_INSTANT = Instant.ofEpochMilli(946659600000L);
    public static final Instant MAX_INSTANT = Instant.ofEpochMilli(2147446800000L);
    private DateTimeUtils() {
    }

    public static LocalDateTime toLocalDateTime(final String dateTimeString, String pattern) {
        if (Objects.isNull(dateTimeString)) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
