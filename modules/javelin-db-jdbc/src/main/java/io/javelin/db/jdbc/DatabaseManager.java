package io.javelin.db.jdbc;

import io.javelin.core.Database;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Explicit registry of named database connections with one default. */
public final class DatabaseManager implements AutoCloseable {
    private final Map<String, Database> databases;
    private final String defaultName;

    public DatabaseManager(Map<String, Database> databases, String defaultName) {
        if (!databases.containsKey(defaultName)) {
            throw new IllegalArgumentException("Unknown default database: " + defaultName);
        }
        this.databases = Map.copyOf(new LinkedHashMap<>(databases));
        this.defaultName = defaultName;
    }

    public Database database(String name) {
        Database database = databases.get(name);
        if (database == null) {
            throw new IllegalArgumentException("Unknown database connection: " + name);
        }
        return database;
    }

    public Database defaultDatabase() {
        return database(defaultName);
    }

    public String defaultName() {
        return defaultName;
    }

    public Set<String> names() {
        return databases.keySet();
    }

    @Override
    public void close() {
        for (Database database : databases.values()) {
            database.close();
        }
    }
}
