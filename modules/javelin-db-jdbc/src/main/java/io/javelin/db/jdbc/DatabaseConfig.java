package io.javelin.db.jdbc;

import io.javelin.core.Config;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/** Reads explicit {@code database.*} keys into one or more named connections. */
public final class DatabaseConfig {
    private static final String CONNECTIONS = "database.connections.";

    private final String defaultName;
    private final Map<String, Connection> connections;

    private DatabaseConfig(String defaultName, Map<String, Connection> connections) {
        this.defaultName = defaultName;
        this.connections = Map.copyOf(connections);
    }

    public record Connection(String name, String url, String driver, String username, String password,
                             int maxPoolSize, int minIdle, long connectionTimeoutMs,
                             long idleTimeoutMs, long maxLifetimeMs) {
    }

    public static DatabaseConfig from(Config config) {
        Set<String> names = connectionNames(config);
        Map<String, Connection> connections = new LinkedHashMap<>();
        for (String name : names) {
            connections.put(name, connection(config, name));
        }
        String configuredDefault = config.getString("database.default", "default");
        String defaultName = connections.containsKey(configuredDefault)
                ? configuredDefault
                : connections.keySet().stream().findFirst().orElse(configuredDefault);
        return new DatabaseConfig(defaultName, connections);
    }

    public String defaultName() {
        return defaultName;
    }

    public Connection defaultConnection() {
        return connections.get(defaultName);
    }

    public Map<String, Connection> connections() {
        return connections;
    }

    private static Set<String> connectionNames(Config config) {
        Set<String> names = new LinkedHashSet<>();
        for (String key : config.keys()) {
            if (key.startsWith(CONNECTIONS)) {
                String rest = key.substring(CONNECTIONS.length());
                int dot = rest.indexOf('.');
                names.add(dot < 0 ? rest : rest.substring(0, dot));
            }
        }
        if (names.isEmpty()) {
            names.add(config.getString("database.default", "default"));
        }
        return names;
    }

    private static Connection connection(Config config, String name) {
        String prefix = CONNECTIONS + name + ".";
        String driver = config.getString(prefix + "driver", config.getString("database.driver", "h2"));
        String url = config.getString(prefix + "url").orElseGet(() -> url(config, prefix, driver));
        int maxPoolSize = config.getInt(prefix + "max_pool_size", config.getInt("database.pool.max", 10));
        if (maxPoolSize < 1) {
            throw new IllegalArgumentException("database connection '" + name + "' max_pool_size must be at least 1");
        }
        return new Connection(
                name,
                url,
                driver,
                config.getString(prefix + "username", config.getString("database.username", "")),
                config.getString(prefix + "password", config.getString("database.password", "")),
                maxPoolSize,
                config.getInt(prefix + "minimum_idle", 0),
                config.getLong(prefix + "connection_timeout_ms", 30_000L),
                config.getLong(prefix + "idle_timeout_ms", 600_000L),
                config.getLong(prefix + "max_lifetime_ms", 1_800_000L));
    }

    private static String url(Config config, String prefix, String driver) {
        if (driver.toLowerCase(java.util.Locale.ROOT).contains("sqlite")) {
            return "jdbc:sqlite:" + config.getString(prefix + "database", "app.db");
        }
        String host = config.getString(prefix + "host", config.getString("database.host", "localhost"));
        int port = config.getInt(prefix + "port", config.getInt("database.port", 5432));
        String database = config.getString(prefix + "database", config.getString("database.database", "app"));
        return "jdbc:" + driver + "://" + host + ":" + port + "/" + database;
    }
}
