package io.javelin.cli.commands;

import io.javelin.cli.CommandContext;
import io.javelin.cli.ConsoleKernel;
import io.javelin.cli.output.ConsoleOutput;
import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.Database;
import io.javelin.core.Env;
import io.javelin.db.jdbc.MigrationRunner;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MigrationCommandTest {
    @Test
    void migrateCommandRunsYamlMigrationFiles() throws Exception {
        Path root = Files.createTempDirectory("javelin-migrate-command");
        Path migrations = root.resolve("database/migrations");
        Files.createDirectories(migrations);
        Files.writeString(migrations.resolve("20260501010101_create_users.yml"), """
                name: create_users_table
                up:
                  - sql: create table users (id bigint)
                down:
                  - sql: drop table users
                """);

        RecordingDatabase database = new RecordingDatabase();
        MigrationRunner runner = new MigrationRunner(database, migrations);
        Application app = new Application(emptyConfig(), emptyEnv());
        app.singleton(MigrationRunner.class, () -> runner);
        app.singleton(Database.class, () -> database);

        StringWriter output = new StringWriter();
        CommandContext context = new CommandContext(app, root, new ConsoleOutput(new PrintWriter(output, true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"migrate"});

        assertEquals(0, exit);
        assertTrue(output.toString().contains("Applied migrations: create_users_table"));
    }

    @Test
    void rollbackCommandReplaysYamlDownStatements() throws Exception {
        Path root = Files.createTempDirectory("javelin-rollback-command");
        Path migrations = root.resolve("database/migrations");
        Files.createDirectories(migrations);
        Files.writeString(migrations.resolve("20260501010101_create_users.yml"), """
                name: create_users_table
                up:
                  - sql: create table users (id bigint)
                down:
                  - sql: drop table users
                """);

        RecordingDatabase database = new RecordingDatabase();
        database.historyRows.add(historyRow(1L, "20260501010101_create_users.yml", 3));
        MigrationRunner runner = new MigrationRunner(database, migrations);
        Application app = new Application(emptyConfig(), emptyEnv());
        app.singleton(MigrationRunner.class, () -> runner);
        app.singleton(Database.class, () -> database);

        StringWriter output = new StringWriter();
        CommandContext context = new CommandContext(app, root, new ConsoleOutput(new PrintWriter(output, true), false));

        int exit = new ConsoleKernel(context).run(new String[]{"migrate:rollback"});

        assertEquals(0, exit);
        assertTrue(output.toString().contains("Rolled back migrations: create_users_table"));
    }

    private static Config emptyConfig() {
        return new Config() {
            @Override
            public Optional<String> getString(String key) {
                return Optional.empty();
            }

            @Override
            public Optional<Integer> getInt(String key) {
                return Optional.empty();
            }

            @Override
            public Optional<Boolean> getBoolean(String key) {
                return Optional.empty();
            }

            @Override
            public List<String> getStringList(String key) {
                return List.of();
            }
        };
    }

    private static Env emptyEnv() {
        return new Env() {
            @Override
            public Optional<String> get(String key) {
                return Optional.empty();
            }
        };
    }

    private static Map<String, Object> historyRow(long id, String migrationName, int batch) {
        Map<String, Object> row = new LinkedHashMap<>();
        row.put("id", id);
        row.put("migration_name", migrationName);
        row.put("batch", batch);
        row.put("ran_at", Timestamp.from(Instant.parse("2026-05-01T00:00:00Z")));
        return row;
    }

    private static final class RecordingDatabase implements Database {
        private final List<String> executedSql = new ArrayList<>();
        private final List<Map<String, Object>> insertedHistory = new ArrayList<>();
        private final List<Map<String, Object>> historyRows = new ArrayList<>();
        private final List<Long> deletedIds = new ArrayList<>();

        @Override
        public io.javelin.core.QueryBuilder table(String table) {
            return new RecordingQueryBuilder(table);
        }

        @Override
        public int execute(String sql) {
            executedSql.add(sql.stripIndent().strip());
            return 1;
        }

        @Override
        public <T> T transaction(io.javelin.core.TransactionCallback<T> callback) {
            try {
                return callback.run(this);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        @Override
        public void close() {
        }

        private final class RecordingQueryBuilder implements io.javelin.core.QueryBuilder {
            private final String table;
            private String whereColumn;
            private Object whereValue;

            private RecordingQueryBuilder(String table) {
                this.table = table;
            }

            @Override
            public io.javelin.core.QueryBuilder where(String column, Object value) {
                this.whereColumn = column;
                this.whereValue = value;
                return this;
            }

            @Override
            public Optional<Map<String, Object>> first() {
                return historyRows.stream().findFirst().map(LinkedHashMap::new);
            }

            @Override
            public List<Map<String, Object>> get() {
                return historyRows.stream().map(row -> (Map<String, Object>) new LinkedHashMap<>(row)).toList();
            }

            @Override
            public List<Map<String, Object>> paginate(int page, int perPage) {
                return List.of();
            }

            @Override
            public long insert(Map<String, Object> values) {
                if ("javelin_migrations".equals(table)) {
                    insertedHistory.add(new LinkedHashMap<>(values));
                    Map<String, Object> row = new LinkedHashMap<>(values);
                    row.putIfAbsent("id", (long) insertedHistory.size());
                    historyRows.add(row);
                    return insertedHistory.size();
                }
                return 0L;
            }

            @Override
            public int update(Map<String, Object> values) {
                return 0;
            }

            @Override
            public int delete() {
                if (!"javelin_migrations".equals(table) || whereColumn == null) {
                    return 0;
                }
                long id = whereValue instanceof Number number ? number.longValue() : Long.parseLong(whereValue.toString());
                boolean removed = historyRows.removeIf(row -> {
                    Object value = row.get("id");
                    long current = value instanceof Number number ? number.longValue() : Long.parseLong(String.valueOf(value));
                    if (current == id) {
                        deletedIds.add(current);
                        return true;
                    }
                    return false;
                });
                return removed ? 1 : 0;
            }
        }
    }
}
