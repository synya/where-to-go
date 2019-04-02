package com.mycompany.wheretogo.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final LocalDate MIN_DATE = LocalDate.of(2000, 1, 1);
    private static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private DateUtil() {
    }

    public static LocalDate adjustStartDate(LocalDate localDate) {
        return adjustDate(localDate, MIN_DATE);
    }

    public static LocalDate adjustEndDate(LocalDate localDate) {
        return adjustDate(localDate, MAX_DATE);
    }

    public static LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str, DateTimeFormatter.ISO_DATE);
    }

    private static LocalDate adjustDate(LocalDate localDate, LocalDate defaultDate) {
        return localDate == null ? defaultDate : localDate;
    }
}
