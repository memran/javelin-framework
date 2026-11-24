package io.javelin.cache.valkey;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javelin.cache.Cache;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/** Cache backed by Valkey (or any Redis-protocol server) using Lettuce. */
public final class ValkeyCache implements Cache, AutoCloseable {
    private static final ObjectMapper JSON = new ObjectMapper();

    private final RedisClient client;
    private StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> commands;

    public ValkeyCache(String url) {
        this(RedisClient.create(url));
    }

    public ValkeyCache(RedisClient client) {
        this.client = Objects.requireNonNull(client, "client");
    }

    private synchronized RedisCommands<String, String> commands() {
        if (commands == null) {
            connection = client.connect();
            commands = connection.sync();
        }
        return commands;
    }

    @Override
    public void put(String key, Object value, Duration ttl) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(ttl, "ttl");
        long seconds = Math.max(1, ttl.toSeconds());
        commands().setex(key, seconds, encode(value));
    }

    @Override
    public Optional<Object> get(String key) {
        Objects.requireNonNull(key, "key");
        String json = commands().get(key);
        return json == null ? Optional.empty() : Optional.ofNullable(decode(json));
    }

    @Override
    public void forget(String key) {
        Objects.requireNonNull(key, "key");
        commands().del(key);
    }

    @Override
    public void close() {
        if (connection != null) {
            connection.close();
        }
        client.shutdown();
    }

    static String encode(Object value) {
        try {
            return JSON.writeValueAsString(value);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Unable to serialize cache value", exception);
        }
    }

    static Object decode(String json) {
        try {
            return JSON.readValue(json, Object.class);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Unable to deserialize cache value", exception);
        }
    }
}
