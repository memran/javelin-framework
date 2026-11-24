package io.javelin.db.migration;

import io.javelin.core.Database;
import io.javelin.core.QueryBuilder;
import io.javelin.core.TransactionCallback;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class MigrationDialectsTest {
    @Test
    void selectsDialectFromDriverOrUrl() {
        assertTrue(MigrationDialects.from("org.postgresql.Driver") == MigrationDialects.POSTGRESQL);
        assertTrue(MigrationDialects.from("jdbc:mysql://db:3306/app") == MigrationDialects.MYSQL);
        assertTrue(MigrationDialects.from("org.mariadb.jdbc.Driver") == MigrationDialects.MYSQL);
        assertTrue(MigrationDialects.from("jdbc:sqlite:app.db") == MigrationDialects.SQLITE);
    }

    @Test
    void writesDialectSpecificHistoryTable() throws Exception {
        Path dir = Files.createTempDirectory("javelin-dialect");
        RecordingDatabase database = new RecordingDatabase();

        new MigrationRunner(database, dir, MigrationDialects.SQLITE).migrate();

        assertEquals(1, database.sql.size());
        assertTrue(database.sql.get(0).contains("integer primary key autoincrement"), database.sql.get(0));
    }

    private static final class RecordingDatabase implements Database {
        private final java.util.List<String> sql = new java.util.ArrayList<>();

        @Override public QueryBuilder table(String table) {
            return new QueryBuilder() {
                @Override public QueryBuilder where(String column, Object value) { return this; }
                @Override public java.util.Optional<Map<String, Object>> first() { return java.util.Optional.empty(); }
                @Override public List<Map<String, Object>> get() { return List.of(); }
                @Override public List<Map<String, Object>> paginate(int page, int perPage) { return List.of(); }
                @Override public long insert(Map<String, Object> values) { return 0; }
                @Override public int update(Map<String, Object> values) { return 0; }
                @Override public int delete() { return 0; }
            };
        }
        @Override public int execute(String statement) { sql.add(statement); return 0; }
        @Override public <T> T transaction(TransactionCallback<T> callback) {
            try {
                return callback.run(this);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
        @Override public void close() { }
    }
}
