package io.javelin.support;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;

public final class Str {
    private Str() {
    }

    public static boolean isBlank(CharSequence value) {
        return value == null || value.toString().isBlank();
    }

    public static boolean isNotBlank(CharSequence value) {
        return !isBlank(value);
    }

    public static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public static String defaultIfBlank(String value, String fallback) {
        return isBlank(value) ? fallback : value;
    }

    public static String toSlug(String value) {
        Objects.requireNonNull(value, "value");
        String normalized = stripAccents(value);
        normalized = normalized.replaceAll("[^\\p{Alnum}]+", " ").trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()) {
            return "";
        }
        return normalized.replaceAll("\\s+", "-");
    }

    public static String toSnakeCase(String value) {
        Objects.requireNonNull(value, "value");
        String normalized = stripAccents(value)
                .replaceAll("([a-z0-9])([A-Z])", "$1_$2")
                .replaceAll("[^\\p{Alnum}]+", "_")
                .replaceAll("_+", "_")
                .replaceAll("^_|_$", "");
        return normalized.toLowerCase(Locale.ROOT);
    }

    public static String toCamelCase(String value) {
        Objects.requireNonNull(value, "value");
        String[] parts = stripAccents(value).replaceAll("[^\\p{Alnum}]+", " ").trim().split("\\s+");
        if (parts.length == 0 || parts[0].isBlank()) {
            return "";
        }

        StringBuilder result = new StringBuilder(parts[0].toLowerCase(Locale.ROOT));
        for (int index = 1; index < parts.length; index++) {
            String part = parts[index];
            if (part.isBlank()) {
                continue;
            }
            result.append(part.substring(0, 1).toUpperCase(Locale.ROOT));
            if (part.length() > 1) {
                result.append(part.substring(1).toLowerCase(Locale.ROOT));
            }
        }
        return result.toString();
    }

    public static String capitalize(String value) {
        Objects.requireNonNull(value, "value");
        if (value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase(Locale.ROOT) + value.substring(1);
    }

    public static String repeat(String value, int count) {
        Objects.requireNonNull(value, "value");
        if (count < 0) {
            throw new IllegalArgumentException("count must be greater than or equal to zero");
        }
        return value.repeat(count);
    }

    public static String mask(String value, int visiblePrefix, int visibleSuffix, char maskChar) {
        Objects.requireNonNull(value, "value");
        requireNonNegative(visiblePrefix, "visiblePrefix");
        requireNonNegative(visibleSuffix, "visibleSuffix");
        if (visiblePrefix + visibleSuffix >= value.length()) {
            return value;
        }

        StringBuilder result = new StringBuilder(value.length());
        result.append(value, 0, visiblePrefix);
        for (int index = visiblePrefix; index < value.length() - visibleSuffix; index++) {
            result.append(maskChar);
        }
        result.append(value, value.length() - visibleSuffix, value.length());
        return result.toString();
    }

    private static String stripAccents(String value) {
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFKD);
        return normalized.replaceAll("\\p{M}+", "");
    }

    private static void requireNonNegative(int value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must be greater than or equal to zero");
        }
    }
}
