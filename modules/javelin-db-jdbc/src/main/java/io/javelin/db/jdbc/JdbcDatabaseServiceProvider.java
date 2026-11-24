package io.javelin.db.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import io.javelin.core.Application;
import io.javelin.core.Database;
import io.javelin.core.Logger;
import io.javelin.core.ServiceProvider;
import io.javelin.db.migration.MigrationDialects;
import io.javelin.db.migration.MigrationRunner;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/** Wires named database connections, query timing and migrations. */
public final class JdbcDatabaseServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(DatabaseConfig.class, () -> DatabaseConfig.from(app.config()));
        app.singleton(QueryTimingConfig.class, () -> QueryTimingConfig.from(app.config()));
        app.singleton(SqlTimer.class, () -> new SqlTimer(
                app.has(Logger.class) ? app.make(Logger.class) : new NoopLogger(),
                app.make(QueryTimingConfig.class)));
        app.singleton(DatabaseManager.class, () -> createManager(app));
        app.singleton(Database.class, () -> app.make(DatabaseManager.class).defaultDatabase());
        app.singleton(MigrationRunner.class, () -> createMigrationRunner(app));
        app.singleton(io.javelin.db.jdbc.MigrationRunner.class,
                () -> new io.javelin.db.jdbc.MigrationRunner(
                        app.make(Database.class), migrationsDirectory(app), dialect(app)));
    }

    private DatabaseManager createManager(Application app) {
        SqlTimer timer = app.make(SqlTimer.class);
        Map<String, Database> databases = new LinkedHashMap<>();
        for (Map.Entry<String, DatabaseConfig.Connection> entry : app.make(DatabaseConfig.class).connections().entrySet()) {
            databases.put(entry.getKey(), new JdbcDatabase(hikari(entry.getValue()), timer));
        }
        DatabaseConfig config = app.make(DatabaseConfig.class);
        return new DatabaseManager(databases, config.defaultName());
    }

    /** No-arg datasource defers pool startup until the first connection is requested. */
    private HikariDataSource hikari(DatabaseConfig.Connection connection) {
        HikariDataSource config = new HikariDataSource();
        config.setJdbcUrl(connection.url());
        config.setUsername(connection.username());
        config.setPassword(connection.password());
        config.setMaximumPoolSize(connection.maxPoolSize());
        config.setMinimumIdle(connection.minIdle());
        config.setConnectionTimeout(connection.connectionTimeoutMs());
        config.setIdleTimeout(connection.idleTimeoutMs());
        config.setMaxLifetime(connection.maxLifetimeMs());
        return config;
    }

    private MigrationRunner createMigrationRunner(Application app) {
        return new MigrationRunner(app.make(Database.class), migrationsDirectory(app), dialect(app));
    }

    private io.javelin.db.migration.MigrationDialect dialect(Application app) {
        DatabaseConfig.Connection connection = app.make(DatabaseConfig.class).defaultConnection();
        return MigrationDialects.from(connection.driver() + " " + connection.url());
    }

    private Path migrationsDirectory(Application app) {
        Path root = app.has(Path.class)
                ? app.make(Path.class)
                : Path.of(System.getProperty("user.dir"));
        return root.toAbsolutePath().normalize().resolve(app.config().getString("database.migrations_dir", "database/migrations"));
    }

    private static final class NoopLogger implements Logger {
        @Override public void info(String message) { }
        @Override public void warn(String message) { }
        @Override public void error(String message, Throwable throwable) { }
    }
}
