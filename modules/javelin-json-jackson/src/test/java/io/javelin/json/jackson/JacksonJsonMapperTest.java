package io.javelin.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.Json;
import io.javelin.core.JsonMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class JacksonJsonMapperTest {
    @Test
    void serializesAndDeserializesWithSuppliedMapper() {
        JacksonJsonMapper mapper = new JacksonJsonMapper(new ObjectMapper());
        String json = mapper.write(Map.of("name", "Javelin"));

        assertEquals("{\"name\":\"Javelin\"}", json);
        assertTrue(mapper.read(json, Map.class).containsKey("name"));
    }

    @Test
    void providerMakesConfiguredMapperActive() {
        JsonMapper previous = Json.mapper();
        try {
            Application app = new Application(new TestConfig(Map.of(
                    "json.indent_output", "true",
                    "json.fail_on_unknown_properties", "false",
                    "json.write_nulls", "false")), key -> Optional.empty());
            app.register(new JacksonJsonServiceProvider());
            app.boot();

            assertInstanceOf(JacksonJsonMapper.class, Json.mapper());
            assertTrue(Json.stringify(Map.of("a", 1)).contains("\n"));
        } finally {
            Json.use(previous);
        }
    }

    private record TestConfig(Map<String, String> values) implements Config {
        @Override public Optional<String> getString(String key) { return Optional.ofNullable(values.get(key)); }
        @Override public Optional<Integer> getInt(String key) { return getString(key).map(Integer::parseInt); }
        @Override public Optional<Boolean> getBoolean(String key) { return getString(key).map(Boolean::parseBoolean); }
        @Override public List<String> getStringList(String key) { return List.of(); }
    }
}
