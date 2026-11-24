package io.javelin.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

/** Static facade for JSON responses and mapping. The mapper is replaceable through {@link #use}. */
public final class Json {
    private static volatile JsonMapper mapper = new DefaultJsonMapper();

    private Json() {
    }

    public static void use(JsonMapper replacement) {
        mapper = Objects.requireNonNull(replacement, "mapper");
    }

    public static JsonMapper mapper() {
        return mapper;
    }

    public static JsonResponse ok(Object data) {
        return JsonResponse.ok(data);
    }

    public static JsonResponse error(String message, int status) {
        return JsonResponse.error(message, status);
    }

    public static String stringify(Object value) {
        return mapper.write(value);
    }

    public static <T> T parse(String json, Class<T> type) {
        return mapper.read(json, type);
    }

    public static <T> T convert(Object value, Class<T> type) {
        return mapper.convert(value, type);
    }

    private static final class DefaultJsonMapper implements JsonMapper {
        private final ObjectMapper mapper = new ObjectMapper();

        @Override
        public String write(Object value) {
            try {
                return mapper.writeValueAsString(value);
            } catch (Exception exception) {
                throw new IllegalArgumentException("Unable to serialize value as JSON", exception);
            }
        }

        @Override
        public <T> T read(String json, Class<T> type) {
            try {
                return mapper.readValue(json, type);
            } catch (Exception exception) {
                throw new IllegalArgumentException("Unable to parse JSON as " + type.getName(), exception);
            }
        }
    }
}
