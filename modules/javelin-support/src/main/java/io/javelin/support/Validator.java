package io.javelin.support;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public final class Validator {
    private Validator() {
    }

    public static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void requireState(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    public static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static String requireNonBlank(String value, String message) {
        if (Str.isBlank(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static String requireLength(String value, int minInclusive, int maxInclusive, String message) {
        requireNonNull(value, message);
        require(minInclusive >= 0, "minInclusive must be greater than or equal to zero");
        require(maxInclusive >= minInclusive, "maxInclusive must be greater than or equal to minInclusive");
        int length = value.length();
        if (length < minInclusive || length > maxInclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static int requireBetween(int value, int minInclusive, int maxInclusive, String message) {
        require(minInclusive <= maxInclusive, "maxInclusive must be greater than or equal to minInclusive");
        if (value < minInclusive || value > maxInclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static long requireBetween(long value, long minInclusive, long maxInclusive, String message) {
        require(minInclusive <= maxInclusive, "maxInclusive must be greater than or equal to minInclusive");
        if (value < minInclusive || value > maxInclusive) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static <T> T requireOneOf(T value, Collection<? extends T> allowed, String message) {
        Objects.requireNonNull(allowed, "allowed");
        if (!allowed.contains(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static <T> Collection<T> requireNotEmpty(Collection<T> value, String message) {
        requireNonNull(value, message);
        if (value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static <K, V> Map<K, V> requireNotEmpty(Map<K, V> value, String message) {
        requireNonNull(value, message);
        if (value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static String requireMatches(String value, Pattern pattern, String message) {
        requireNonNull(value, message);
        requireNonNull(pattern, "pattern");
        if (!pattern.matcher(value).matches()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
