package io.javelin.core;

/** Explicit JSON mapping contract. Adapters supply the concrete implementation. */
public interface JsonMapper {
    String write(Object value);

    <T> T read(String json, Class<T> type);

    default <T> T convert(Object value, Class<T> type) {
        return read(write(value), type);
    }
}
