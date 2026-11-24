package io.javelin.db.jdbc;

import io.javelin.core.Config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

final class MapConfig implements Config {
    private final Map<String, String> values;

    MapConfig(Map<String, String> values) {
        this.values = new LinkedHashMap<>(values);
    }

    @Override
    public Set<String> keys() {
        return Set.copyOf(values.keySet());
    }

    @Override
    public Optional<String> getString(String key) {
        return Optional.ofNullable(values.get(key));
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
        return List.of();
    }
}
