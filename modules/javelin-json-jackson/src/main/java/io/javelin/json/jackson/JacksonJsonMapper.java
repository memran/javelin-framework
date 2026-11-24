package io.javelin.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javelin.core.JsonMapper;

import java.util.Objects;

/** JSON mapping backed by a caller-supplied Jackson ObjectMapper. */
public final class JacksonJsonMapper implements JsonMapper {
    private final ObjectMapper mapper;

    public JacksonJsonMapper() {
        this(new ObjectMapper());
    }

    public JacksonJsonMapper(ObjectMapper mapper) {
        this.mapper = Objects.requireNonNull(mapper, "mapper");
    }

    public ObjectMapper mapper() {
        return mapper;
    }

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
