package br.com.sistema.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public final class DateUtils {

    public static final DateTimeFormatter BR_DATE =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private DateUtils() {}

    public static LocalDate parse(String value) throws DateTimeParseException {
        return LocalDate.parse(value.trim(), BR_DATE);
    }

    public static String format(LocalDate value) {
        return value == null ? "" : value.format(BR_DATE);
    }
}
