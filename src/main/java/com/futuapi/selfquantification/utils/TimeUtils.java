package com.futuapi.selfquantification.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author 周欣
 * @date 2025/4/2 20:30
 */
public class TimeUtils {


    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 将时间戳转换为日期字符串，格式为：yyyy-MM-dd
     *
     * @param timestamp 时间戳（毫秒）
     * @return 格式化后的日期字符串
     */
    public static String timestampToDateString(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault());
        return dateTime.format(DATE_FORMATTER);
    }

    /**
     * 将时间戳转换为日期时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 时间戳（毫秒）
     * @return 格式化后的日期时间字符串
     */
    public static String timestampToDateTimeString(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamp),
            ZoneId.systemDefault());
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * 将秒级时间戳转换为日期字符串，格式为：yyyy-MM-dd
     *
     * @param secondTimestamp 时间戳（秒）
     * @return 格式化后的日期字符串
     */
    public static String secondTimestampToDateString(long secondTimestamp) {
        return timestampToDateString(secondTimestamp * 1000);
    }

}
