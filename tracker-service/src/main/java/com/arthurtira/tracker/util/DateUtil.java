package com.arthurtira.tracker.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    public static Date sevenDaysAgo() {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        return new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
    }

    public static Date endOfDay(Date date) {
        LocalDateTime localDateTime =  date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return  Date.from(localDateTime.toLocalDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static String formatDateForString(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }



}
