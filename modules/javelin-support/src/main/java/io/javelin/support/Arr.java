package io.javelin.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Arr {
    private Arr() {
    }

    public static <T> boolean isEmpty(T[] values) {
        return values == null || values.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] values) {
        return !isEmpty(values);
    }

    public static <T> T first(T[] values) {
        return isEmpty(values) ? null : values[0];
    }

    public static <T> T last(T[] values) {
        return isEmpty(values) ? null : values[values.length - 1];
    }

    public static <T> boolean contains(T[] values, T candidate) {
        if (isEmpty(values)) {
            return false;
        }
        for (T value : values) {
            if (Objects.equals(value, candidate)) {
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> toList(T[] values) {
        if (isEmpty(values)) {
            return List.of();
        }
        return new ArrayList<>(Arrays.asList(values));
    }

    public static <T> List<T> compact(T[] values) {
        if (isEmpty(values)) {
            return List.of();
        }
        List<T> result = new ArrayList<>(values.length);
        for (T value : values) {
            if (value != null) {
                result.add(value);
            }
        }
        return List.copyOf(result);
    }

    public static <T> List<List<T>> chunk(T[] values, int size) {
        return chunk(toList(values), size);
    }

    public static <T> List<List<T>> chunk(List<? extends T> values, int size) {
        Objects.requireNonNull(values, "values");
        requirePositive(size, "size");

        if (values.isEmpty()) {
            return List.of();
        }

        List<List<T>> chunks = new ArrayList<>();
        for (int index = 0; index < values.size(); index += size) {
            int end = Math.min(index + size, values.size());
            chunks.add(new ArrayList<>(values.subList(index, end)));
        }
        return List.copyOf(chunks);
    }

    private static void requirePositive(int value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
    }
}
