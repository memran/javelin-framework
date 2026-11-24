package io.javelin.core;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Json {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Json() {
    }

    public static JsonResponse ok(Object data) {
        return JsonResponse.ok(data);
    }

    public static JsonResponse error(String message, int status) {
        return JsonResponse.error(message, status);
    }

    public static String stringify(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Unable to serialize value as JSON", exception);
        }
    }

    public static <T> T parse(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Unable to parse JSON as " + type.getName(), exception);
        }
    }

    public static <T> T convert(Object value, Class<T> type) {
        return MAPPER.convertValue(value, type);
    }
}
