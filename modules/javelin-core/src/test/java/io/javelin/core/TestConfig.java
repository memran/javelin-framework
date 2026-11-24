package io.javelin.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

final class TestConfig implements Config {
    private final Map<String, Object> values;

    TestConfig(Map<String, Object> values) {
        this.values = new LinkedHashMap<>(values);
    }

    @Override
    public Optional<String> getString(String key) {
        Object value = values.get(key);
        return value == null ? Optional.empty() : Optional.of(value.toString());
    }

    @Override
    public Optional<Integer> getInt(String key) {
        return getString(key).map(Integer::parseInt);
    }

    @Override
    public Optional<Boolean> getBoolean(String key) {
        return getString(key).map(Boolean::parseBoolean);
    }

    @Override
    public List<String> getStringList(String key) {
        Object value = values.get(key);
        if (value instanceof List<?> list) {
            return list.stream().map(String::valueOf).toList();
        }
        return List.of();
    }
}
