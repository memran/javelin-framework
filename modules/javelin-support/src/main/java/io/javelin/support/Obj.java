package io.javelin.support;

import java.util.Objects;
import java.util.function.Supplier;

public final class Obj {
    private Obj() {
    }

    public static <T> T coalesce(T value, T fallback) {
        return value != null ? value : fallback;
    }

    public static <T> T coalesce(T value, Supplier<? extends T> fallbackSupplier) {
        Objects.requireNonNull(fallbackSupplier, "fallbackSupplier");
        return value != null ? value : fallbackSupplier.get();
    }

    public static boolean equalsAny(Object value, Object... candidates) {
        Objects.requireNonNull(candidates, "candidates");
        for (Object candidate : candidates) {
            if (Objects.equals(value, candidate)) {
                return true;
            }
        }
        return false;
    }

    public static int hash(Object... values) {
        Objects.requireNonNull(values, "values");
        return Objects.hash(values);
    }

    public static <T> boolean isType(Object value, Class<T> type) {
        Objects.requireNonNull(type, "type");
        return type.isInstance(value);
    }

    public static <T> T requireType(Object value, Class<T> type) {
        Objects.requireNonNull(type, "type");
        if (!type.isInstance(value)) {
            String actual = value == null ? "null" : value.getClass().getName();
            throw new IllegalArgumentException("Expected type " + type.getName() + " but got " + actual);
        }
        return type.cast(value);
    }
}
