package ru.javawebinar.basejava.util;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
    public static final YearMonth NOW = YearMonth.of(3000, 1);

    public static YearMonth parse(String date) {
        if (date == null || date.isBlank() || date.equals("Сейчас")) {
            return NOW;
        }
        return YearMonth.parse(date, formatter);
    }

    public static String format(YearMonth date) {
        if (date == null) {
            return "";
        }
        return date.equals(NOW) ? "Сейчас" : date.format(formatter);
    }
}
