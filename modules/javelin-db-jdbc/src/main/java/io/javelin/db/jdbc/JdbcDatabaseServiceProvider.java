package io.javelin.db.jdbc;

import com.zaxxer.hikari.HikariConfig;
import io.javelin.core.Application;
import io.javelin.core.Database;
import io.javelin.core.ServiceProvider;

import java.nio.file.Path;

public final class JdbcDatabaseServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(Database.class, () -> new JdbcDatabase(config(app)));
        app.singleton(MigrationRunner.class, () -> new MigrationRunner(app.make(Database.class), migrationsDirectory(app)));
    }

    private HikariConfig config(Application app) {
        HikariConfig config = new HikariConfig();
        String driver = app.config().getString("database.driver", "h2");
        String host = app.config().getString("database.host", "localhost");
        int port = app.config().getInt("database.port", 5432);
        String database = app.config().getString("database.database", "app");
        config.setJdbcUrl(app.config().getString("database.url", "jdbc:" + driver + "://" + host + ":" + port + "/" + database));
        config.setUsername(app.config().getString("database.username", ""));
        config.setPassword(app.config().getString("database.password", ""));
        config.setMaximumPoolSize(app.config().getInt("database.pool.max", 10));
        return config;
    }

    private Path migrationsDirectory(Application app) {
        Path root = app.has(Path.class)
                ? app.make(Path.class)
                : Path.of(System.getProperty("user.dir"));
        return root.toAbsolutePath().normalize().resolve(app.config().getString("database.migrations_dir", "database/migrations"));
    }
}
