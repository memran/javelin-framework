package io.javelin.db.jdbc;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DatabaseConfigTest {
    @Test
    void discoversNamedConnectionsAndDefault() {
        DatabaseConfig config = DatabaseConfig.from(new MapConfig(Map.of(
                "database.default", "analytics",
                "database.connections.pgsql.url", "jdbc:postgresql://db:5432/app",
                "database.connections.pgsql.username", "alice",
                "database.connections.pgsql.max_pool_size", "6",
                "database.connections.analytics.driver", "sqlite",
                "database.connections.analytics.database", "stats.db")));

        assertEquals(java.util.Set.of("pgsql", "analytics"), config.connections().keySet());
        assertEquals("analytics", config.defaultName());
        assertEquals("jdbc:postgresql://db:5432/app", config.connections().get("pgsql").url());
        assertEquals("alice", config.connections().get("pgsql").username());
        assertEquals(6, config.connections().get("pgsql").maxPoolSize());
        assertEquals("jdbc:sqlite:stats.db", config.defaultConnection().url());
        assertEquals(30_000L, config.defaultConnection().connectionTimeoutMs());
    }

    @Test
    void supportsLegacyFlatKeys() {
        DatabaseConfig config = DatabaseConfig.from(new MapConfig(Map.of(
                "database.driver", "postgresql",
                "database.host", "localhost",
                "database.database", "demo",
                "database.pool.max", "4")));

        assertEquals(java.util.Set.of("default"), config.connections().keySet());
        assertEquals("jdbc:postgresql://localhost:5432/demo", config.defaultConnection().url());
        assertEquals(4, config.defaultConnection().maxPoolSize());
    }

    @Test
    void rejectsNonPositivePoolSize() {
        MapConfig values = new MapConfig(Map.of(
                "database.connections.pgsql.url", "jdbc:postgresql://db/app",
                "database.connections.pgsql.max_pool_size", "0"));
        assertThrows(IllegalArgumentException.class, () -> DatabaseConfig.from(values));
    }

    @Test
    void readsQueryTimingKeys() {
        QueryTimingConfig timing = QueryTimingConfig.from(new MapConfig(Map.of(
                "database.query.timing", "true",
                "database.query.slow_ms", "25",
                "database.query.log_all", "true")));
        assertTrue(timing.enabled());
        assertEquals(25L, timing.slowThresholdMs());
        assertTrue(timing.logAll());
    }
}
