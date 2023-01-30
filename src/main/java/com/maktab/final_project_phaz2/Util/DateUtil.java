package com.maktab.final_project_phaz2.Util;

import com.maktab.final_project_phaz2.exception.InputInvalidException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
}