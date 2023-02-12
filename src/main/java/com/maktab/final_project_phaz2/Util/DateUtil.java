package com.maktab.final_project_phaz2.Util;

import com.maktab.final_project_phaz2.exception.InputInvalidException;

import java.time.*;
import java.util.Date;

public class DateUtil {
    public static Date changeLocalDateToDate(LocalDateTime localDate) {
        ZonedDateTime zdt = localDate.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());

    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static boolean isNotDateValid(Date date) {
        LocalDateTime testDate = dateToLocalDateTime(date);
        return testDate.isBefore(LocalDateTime.now());
    }
    public static boolean isCurrentDateForChangeStatus(Date date) {
        LocalDateTime testDate = dateToLocalDateTime(date);
        return testDate.isAfter(LocalDateTime.now());
    }
    public static Duration durationToHour(Long number){
        return Duration.ofHours(number);
    }
    public static Long differentBetweenTwoDate(Date date1,Date date2){
        LocalDateTime localDateTime = dateToLocalDateTime(date1);
        LocalDateTime localDateTime1 = dateToLocalDateTime(date2);
       Duration duration=Duration.between(localDateTime,localDateTime1);
        return duration.toHours();
    }
}
