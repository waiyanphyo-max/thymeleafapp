package com.talentprogram.batch_8.thymeleafapp.formatters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateTimeFormatter {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public String formatDateTime(LocalDateTime dateTime) {

        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DTF);
    }

    public String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DF);
    }
}
