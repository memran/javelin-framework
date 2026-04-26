package io.javelin.core;

import java.util.List;
import java.util.Optional;

public interface Config {
    Optional<String> getString(String key);

    default String getString(String key, String fallback) {
        return getString(key).orElse(fallback);
    }

    Optional<Integer> getInt(String key);

    default int getInt(String key, int fallback) {
        return getInt(key).orElse(fallback);
    }

    Optional<Boolean> getBoolean(String key);

    List<String> getStringList(String key);
}
