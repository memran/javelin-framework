package io.javelin.scheduler;

import io.javelin.core.Database;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Shared lock backed by a database row, working across application instances. */
public final class DatabaseSchedulerLock implements SchedulerLock {
    public static final String TABLE = "javelin_schedule_locks";

    private final Database database;

    public DatabaseSchedulerLock(Database database) {
        this.database = Objects.requireNonNull(database, "database");
        database.execute("""
                create table if not exists javelin_schedule_locks (
                    task_name varchar(255) primary key,
                    owner varchar(255) not null,
                    expires_at bigint not null
                )
                """);
    }

    @Override
    public boolean tryLock(String task, String owner, Duration ttl) {
        long now = System.currentTimeMillis();
        long expires = now + ttl.toMillis();
        Optional<Map<String, Object>> current = database.table(TABLE).where("task_name", task).first();
        if (current.isEmpty()) {
            Map<String, Object> row = new java.util.LinkedHashMap<>();
            row.put("task_name", task);
            row.put("owner", owner);
            row.put("expires_at", expires);
            try {
                database.table(TABLE).insert(row);
                return true;
            } catch (RuntimeException duplicate) {
                // Another instance inserted first; fall through to the CAS update below.
            }
        } else {
            long existingExpiry = number(current.get().get("expires_at"));
            String existingOwner = string(current.get().get("owner"));
            if (existingExpiry > now && !existingOwner.equals(owner)) {
                return false;
            }
        }
        int updated = database.execute("update " + TABLE
                + " set owner = '" + escaped(owner) + "', expires_at = " + expires
                + " where task_name = '" + escaped(task) + "'");
        return updated == 1;
    }

    @Override
    public void release(String task, String owner) {
        database.execute("delete from " + TABLE + " where task_name = '" + escaped(task)
                + "' and owner = '" + escaped(owner) + "'");
    }

    private static String escaped(String value) {
        return value.replace("'", "''");
    }

    private static String string(Object value) {
        return value == null ? "" : value.toString();
    }

    private static long number(Object value) {
        return value instanceof Number number ? number.longValue() : 0L;
    }
}
