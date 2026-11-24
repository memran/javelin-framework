package io.javelin.security;

import io.javelin.core.Session;
import io.javelin.core.SessionStore;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Simple in-process session store; opt into a distributed store by registering another provider. */
public final class InMemorySessionStore implements SessionStore {
    private final Map<String, Map<String, Object>> values = new ConcurrentHashMap<>();
    private final Map<String, Long> expirations = new ConcurrentHashMap<>();
    private final long ttlMillis;

    public InMemorySessionStore(int ttlSeconds) {
        if (ttlSeconds < 1) {
            throw new IllegalArgumentException("session.ttl_seconds must be at least 1");
        }
        this.ttlMillis = ttlSeconds * 1000L;
    }

    @Override
    public Session create() {
        String id = UUID.randomUUID().toString().replace("-", "");
        values.put(id, new ConcurrentHashMap<>());
        expirations.put(id, System.currentTimeMillis() + ttlMillis);
        return new MemorySession(this, id);
    }

    @Override
    public Optional<Session> find(String id) {
        Long expires = expirations.get(id);
        if (expires == null) {
            return Optional.empty();
        }
        if (expires <= System.currentTimeMillis()) {
            remove(id);
            return Optional.empty();
        }
        return Optional.of(new MemorySession(this, id));
    }

    @Override
    public void save(Session session) {
        values.put(session.id(), new LinkedHashMap<>(session.all()));
        expirations.put(session.id(), System.currentTimeMillis() + ttlMillis);
    }

    @Override
    public void remove(String id) {
        values.remove(id);
        expirations.remove(id);
    }

    @Override
    public void close() {
        values.clear();
        expirations.clear();
    }

    Map<String, Object> attributes(String id) {
        return values.computeIfAbsent(id, ignored -> new ConcurrentHashMap<>());
    }

    private static final class MemorySession implements Session {
        private final SessionStore store;
        private final String id;
        private boolean valid = true;

        private MemorySession(SessionStore store, String id) {
            this.store = store;
            this.id = id;
        }

        @Override public String id() { return id; }
        @Override public Object get(String key) { return attributes().get(key); }
        @Override public void put(String key, Object value) { attributes().put(key, value); }
        @Override public void remove(String key) { attributes().remove(key); }
        @Override public Map<String, Object> all() { return Map.copyOf(attributes()); }
        @Override public void clear() { attributes().clear(); }
        @Override public void save() { store.save(this); }
        @Override public void invalidate() {
            store.remove(id);
            attributes().clear();
            valid = false;
        }
        @Override public boolean isValid() { return valid; }

        private Map<String, Object> attributes() {
            return ((InMemorySessionStore) store).attributes(id);
        }
    }
}
