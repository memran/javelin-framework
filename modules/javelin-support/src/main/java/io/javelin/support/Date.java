package io.javelin.support;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;

public final class Date {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private Date() {
    }

    public static LocalDate today() {
        return LocalDate.now(ZoneOffset.UTC);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static Optional<LocalDate> parseDate(String value) {
        return parse(value, LocalDate::parse);
    }

    public static Optional<LocalDateTime> parseDateTime(String value) {
        return parse(value, LocalDateTime::parse);
    }

    public static LocalDate requireDate(String value, String message) {
        return parseDate(value).orElseThrow(() -> new IllegalArgumentException(message));
    }

    public static LocalDateTime requireDateTime(String value, String message) {
        return parseDateTime(value).orElseThrow(() -> new IllegalArgumentException(message));
    }

    public static String format(LocalDate value) {
        return DATE.format(Objects.requireNonNull(value, "value"));
    }

    public static String format(LocalDateTime value) {
        return DATE_TIME.format(Objects.requireNonNull(value, "value"));
    }

    public static LocalDate addDays(LocalDate value, long days) {
        return Objects.requireNonNull(value, "value").plusDays(days);
    }

    public static LocalDate subtractDays(LocalDate value, long days) {
        return Objects.requireNonNull(value, "value").minusDays(days);
    }

    public static boolean isPast(LocalDate value) {
        return Objects.requireNonNull(value, "value").isBefore(today());
    }

    public static boolean isFuture(LocalDate value) {
        return Objects.requireNonNull(value, "value").isAfter(today());
    }

    private static <T> Optional<T> parse(String value, Parser<T> parser) {
        if (Str.isBlank(value)) {
            return Optional.empty();
        }
        try {
            return Optional.of(parser.parse(value.trim()));
        } catch (DateTimeParseException exception) {
            return Optional.empty();
        }
    }

    @FunctionalInterface
    private interface Parser<T> {
        T parse(String value);
    }
}
