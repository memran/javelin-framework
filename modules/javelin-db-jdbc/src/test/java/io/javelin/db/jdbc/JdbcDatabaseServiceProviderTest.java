package io.javelin.db.jdbc;

import io.javelin.core.Application;
import io.javelin.db.migration.MigrationRunner;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class JdbcDatabaseServiceProviderTest {
    @Test
    void registersNamedDatabasesWithBoundedPools() {
        Application app = new Application(new MapConfig(Map.of(
                "database.default", "main",
                "database.connections.main.url", "jdbc:postgresql://localhost:5432/app",
                "database.connections.main.max_pool_size", "5",
                "database.connections.reporting.url", "jdbc:sqlite:reports.db",
                "database.connections.reporting.max_pool_size", "2")), key -> Optional.empty());

        app.register(new JdbcDatabaseServiceProvider());

        DatabaseManager manager = app.make(DatabaseManager.class);
        assertEquals(java.util.Set.of("main", "reporting"), manager.names());
        assertSame(manager.defaultDatabase(), app.make(io.javelin.core.Database.class));
        assertEquals(5, ((com.zaxxer.hikari.HikariDataSource) ((JdbcDatabase) manager.database("main")).dataSource()).getMaximumPoolSize());
        assertEquals(2, ((com.zaxxer.hikari.HikariDataSource) ((JdbcDatabase) manager.database("reporting")).dataSource()).getMaximumPoolSize());
        assertThrows(IllegalArgumentException.class, () -> manager.database("missing"));
        assertInstanceOf(MigrationRunner.class, app.make(MigrationRunner.class));
        assertInstanceOf(io.javelin.db.jdbc.MigrationRunner.class,
                app.make(io.javelin.db.jdbc.MigrationRunner.class));
    }
}
