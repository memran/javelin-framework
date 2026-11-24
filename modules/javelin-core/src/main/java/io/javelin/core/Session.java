package io.javelin.core;

import java.util.Map;

/** Explicit per-request session contract. Implementations decide where state is stored. */
public interface Session {
    String ATTRIBUTE = "javelin.session";

    String id();

    Object get(String key);

    void put(String key, Object value);

    void remove(String key);

    Map<String, Object> all();

    void clear();

    /** Persists changes and refreshes expiry when applicable. */
    void save();

    /** Removes all state; the middleware issues an expired cookie. */
    void invalidate();

    /** False after {@link #invalidate()}. */
    default boolean isValid() {
        return true;
    }
}
