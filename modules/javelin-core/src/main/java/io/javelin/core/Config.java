package io.javelin.core;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Config {
    /** All known leaf keys, used when a consumer must discover documented key patterns. */
    default Set<String> keys() {
        return Set.of();
    }

    Optional<String> getString(String key);

    default String getString(String key, String fallback) {
        return getString(key).orElse(fallback);
    }

    Optional<Integer> getInt(String key);

    default int getInt(String key, int fallback) {
        return getInt(key).orElse(fallback);
    }

    default Optional<Long> getLong(String key) {
        return getString(key).map(Long::parseLong);
    }

    default long getLong(String key, long fallback) {
        return getLong(key).orElse(fallback);
    }

    Optional<Boolean> getBoolean(String key);

    List<String> getStringList(String key);
}
