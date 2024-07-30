package com.yuga.yuga.util;

import com.yuga.yuga.utils.YugaConstants;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
public class DateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YugaConstants.DATE_TIME_FORMAT);
    private static final DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy").withLocale(Locale.ENGLISH);

    public static LocalDate convertStringToLocalDate(String dateString) {
        return LocalDate.parse(dateString, formatter);
    }

    public static Instant getCurrentTimestamp() {
        return Instant.now();
    }

    public static LocalDateTime getCurrentUtcTime() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static String getCurrentMonthYear() {
        return YearMonth.now().format(monthYearFormatter);
    }

    public static String getPreviousMonthYear() {

        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonthDate = currentDate.minusMonths(1);

        return previousMonthDate.format(monthYearFormatter);
    }

}