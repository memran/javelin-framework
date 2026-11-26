package io.javelin.observability;

import com.zaxxer.hikari.HikariDataSource;
import io.javelin.core.Application;
import io.javelin.db.jdbc.DatabaseManager;
import io.javelin.db.jdbc.JdbcDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** Reads HikariCP pool statistics from configured database connections. */
public final class DbPoolStats {
    private DbPoolStats() {
    }

    public static List<PoolStats> collect(Application app) {
        if (!app.has(DatabaseManager.class)) {
            return List.of();
        }
        List<PoolStats> stats = new ArrayList<>();
        DatabaseManager manager = app.make(DatabaseManager.class);
        for (String name : manager.names()) {
            if (!(manager.database(name) instanceof JdbcDatabase database)
                    || !(database.dataSource() instanceof HikariDataSource source)) {
                continue;
            }
            var pool = source.getHikariPoolMXBean();
            if (pool == null) {
                continue;
            }
            stats.add(new PoolStats(name, pool.getActiveConnections(), pool.getIdleConnections(),
                    pool.getTotalConnections(), source.getMaximumPoolSize()));
        }
        return List.copyOf(stats);
    }
}
