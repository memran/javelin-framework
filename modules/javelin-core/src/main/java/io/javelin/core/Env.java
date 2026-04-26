package io.javelin.core;

import java.util.Optional;

public interface Env {
    Optional<String> get(String key);

    default String get(String key, String fallback) {
        return get(key).orElse(fallback);
    }
}
