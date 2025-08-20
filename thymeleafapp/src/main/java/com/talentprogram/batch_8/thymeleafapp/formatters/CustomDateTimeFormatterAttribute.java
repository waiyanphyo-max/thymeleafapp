package com.talentprogram.batch_8.thymeleafapp.formatters;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CustomDateTimeFormatterAttribute {

    @ModelAttribute(name = "dtf")
    public CustomDateTimeFormatter dateTimeFormatter() {
        return new CustomDateTimeFormatter();
    }
}
