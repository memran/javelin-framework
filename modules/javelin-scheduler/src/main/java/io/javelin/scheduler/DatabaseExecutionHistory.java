package io.javelin.scheduler;

import io.javelin.core.Database;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Durable execution history using the existing database adapter. */
public final class DatabaseExecutionHistory implements ExecutionHistory {
    public static final String TABLE = "javelin_schedule_history";

    private final Database database;

    public DatabaseExecutionHistory(Database database) {
        this.database = Objects.requireNonNull(database, "database");
        database.execute("""
                create table if not exists javelin_schedule_history (
                    id varchar(64) primary key,
                    task_name varchar(255) not null,
                    started_at bigint not null,
                    finished_at bigint not null,
                    success integer not null,
                    error varchar(2000) not null default ''
                )
                """);
    }

    @Override
    public void record(ExecutionRecord execution) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", java.util.UUID.randomUUID().toString());
        row.put("task_name", execution.task());
        row.put("started_at", execution.startedAt().toEpochMilli());
        row.put("finished_at", execution.finishedAt().toEpochMilli());
        row.put("success", execution.success() ? 1 : 0);
        row.put("error", trim(execution.error()));
        database.table(TABLE).insert(row);
    }

    @Override
    public List<ExecutionRecord> recent(int limit) {
        List<ExecutionRecord> records = new ArrayList<>();
        for (Map<String, Object> row : database.table(TABLE).get()) {
            records.add(new ExecutionRecord(
                    string(row.get("task_name")),
                    Instant.ofEpochMilli(number(row.get("started_at"))),
                    Instant.ofEpochMilli(number(row.get("finished_at"))),
                    number(row.get("success")) == 1,
                    string(row.get("error"))));
        }
        records.sort(Comparator.comparing(ExecutionRecord::startedAt).reversed());
        return List.copyOf(records.stream().limit(Math.max(0, limit)).toList());
    }

    private static String trim(String value) {
        if (value == null) {
            return "";
        }
        return value.length() > 1900 ? value.substring(0, 1900) : value;
    }

    private static String string(Object value) {
        return value == null ? "" : value.toString();
    }

    private static long number(Object value) {
        return value instanceof Number number ? number.longValue() : 0L;
    }
}
