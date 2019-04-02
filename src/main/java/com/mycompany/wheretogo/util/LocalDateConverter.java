package com.mycompany.wheretogo.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

import static com.mycompany.wheretogo.util.DateUtil.parseLocalDate;

public class LocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return parseLocalDate(source);
    }
}