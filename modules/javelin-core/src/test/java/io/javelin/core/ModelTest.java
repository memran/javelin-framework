package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ModelTest {
    @Test
    void modelUsesTableConventionAndPersistsThroughTheDatabase() {
        RecordingDatabase database = new RecordingDatabase();
        SampleUser user = new SampleUser(database, "Alice");

        assertEquals("sample_users", user.table());
        assertFalse(user.exists());
        assertEquals(Map.of("name", "Alice"), user.toMap());

        long id = user.save();

        assertEquals(41L, id);
        assertTrue(user.exists());
        assertEquals(Optional.of(41L), user.id());
        assertEquals("sample_users", database.table);
        assertEquals(Map.of("name", "Alice"), database.inserted);

        SampleUser existing = new SampleUser(database, 41L, "Alice");
        assertEquals(41L, existing.save());
        assertEquals(Map.of("name", "Alice"), database.updated);
        assertEquals(1, existing.delete());
        assertEquals("sample_users", database.table);
        assertEquals("id", database.whereColumn);
        assertEquals(41L, database.whereValue);
        assertTrue(database.deleted);
    }

    @Test
    void modelCanFindAndHydrateRowsFromTheDatabase() {
        RecordingDatabase database = new RecordingDatabase();
        database.firstRow = Map.of("id", 7L, "name", "Carol");
        database.rows = List.of(
                Map.of("id", 7L, "name", "Carol"),
                Map.of("id", 8L, "name", "Dave")
        );

        SampleHydratedUser found = Model.find(database, SampleHydratedUser.class, 7L).orElseThrow();
        SampleHydratedUser failFast = Model.findOrFail(database, SampleHydratedUser.class, 7L);
        List<SampleHydratedUser> all = Model.all(database, SampleHydratedUser.class);
        SampleHydratedUser firstWhere = Model.firstWhere(database, SampleHydratedUser.class, "name", "Carol").orElseThrow();
        QueryBuilder query = Model.where(database, SampleHydratedUser.class, "name", "Carol");

        assertEquals(Optional.of(7L), found.id());
        assertEquals(Optional.of(7L), failFast.id());
        assertEquals(Optional.of("Carol"), found.name());
        assertEquals(Optional.of("Carol"), firstWhere.name());
        assertEquals("sample_hydrated_users", found.table());
        assertEquals(2, all.size());
        assertEquals(Optional.of("Carol"), all.get(0).name());
        assertEquals(Optional.of("Dave"), all.get(1).name());
        assertEquals("sample_hydrated_users", database.table);
        assertEquals("name", database.whereColumn);
        assertEquals("Carol", database.whereValue);
        assertEquals(query, database.lastQuery);
        RecordingDatabase missingDatabase = new RecordingDatabase();
        assertTrue(Model.find(missingDatabase, SampleHydratedUser.class, 999L).isEmpty());
        IllegalStateException failure = org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class, () ->
                Model.findOrFail(missingDatabase, SampleHydratedUser.class, 999L));
        assertTrue(failure.getMessage().contains("not found"));
    }

    @Test
    void modelHonorsFillableAndGuardedAttributes() {
        RecordingDatabase database = new RecordingDatabase();
        EditableUser user = new EditableUser(database);

        user.fill(Map.of(
                "name", "Alice",
                "role", "admin",
                "secret", "hidden",
                "id", 99L
        ));

        assertEquals(Optional.of("Alice"), user.attribute("name"));
        assertTrue(user.attribute("role").isEmpty());
        assertTrue(user.attribute("secret").isEmpty());
        assertTrue(user.attribute("id").isEmpty());
        assertEquals(Map.of("name", "Alice"), user.toMap());

        user.forceFill(Map.of(
                "role", "admin",
                "secret", "hidden",
                "id", 99L
        ));

        assertEquals(Optional.of("admin"), user.attribute("role"));
        assertEquals(Optional.of("hidden"), user.attribute("secret"));
        assertTrue(user.attribute("id").isEmpty());
        assertEquals(Map.of("name", "Alice", "role", "admin", "secret", "hidden"), user.toMap());
    }

    @Test
    void modelCastsValuesExplicitly() {
        RecordingDatabase database = new RecordingDatabase();
        CastedArticle article = new CastedArticle(database);

        article.forceFill(Map.of(
                "title", "Hello",
                "published_on", "2026-05-01",
                "published_at", "2026-05-01T13:45:12",
                "views", "42",
                "featured", "yes",
                "status", "published"
        ));

        assertEquals(Optional.of("Hello"), article.attribute("title").map(String.class::cast));
        assertEquals(Optional.of(LocalDate.of(2026, 5, 1)), article.attribute("published_on").map(LocalDate.class::cast));
        assertEquals(Optional.of(LocalDateTime.of(2026, 5, 1, 13, 45, 12)), article.attribute("published_at").map(LocalDateTime.class::cast));
        assertEquals(Optional.of(42), article.attribute("views").map(Integer.class::cast));
        assertEquals(Optional.of(true), article.attribute("featured").map(Boolean.class::cast));
        assertEquals(Optional.of(Status.PUBLISHED), article.attribute("status").map(Status.class::cast));
        assertEquals(Map.of(
                "title", "Hello",
                "published_on", LocalDate.of(2026, 5, 1),
                "published_at", LocalDateTime.of(2026, 5, 1, 13, 45, 12),
                "views", 42,
                "featured", true,
                "status", Status.PUBLISHED
        ), article.toMap());
    }

    private static final class SampleUser extends Model {
        private final String name;

        private SampleUser(Database database, String name) {
            super(database);
            this.name = name;
        }

        private SampleUser(Database database, Long id, String name) {
            super(database, id);
            this.name = name;
        }

        @Override
        protected Map<String, Object> attributes() {
            Map<String, Object> values = new LinkedHashMap<>();
            values.put("name", name);
            return values;
        }
    }

    private static final class SampleHydratedUser extends Model {
        private SampleHydratedUser(Database database) {
            super(database);
        }

        private Optional<String> name() {
            return attribute("name").map(String.class::cast);
        }
    }

    private static final class EditableUser extends Model {
        private EditableUser(Database database) {
            super(database);
        }

        @Override
        protected List<String> fillable() {
            return List.of("name", "role");
        }

        @Override
        protected List<String> guarded() {
            return List.of("role");
        }
    }

    private static final class CastedArticle extends Model {
        private CastedArticle(Database database) {
            super(database);
        }

        @Override
        protected Map<String, Class<?>> casts() {
            return Map.of(
                    "published_on", LocalDate.class,
                    "published_at", LocalDateTime.class,
                    "views", Integer.class,
                    "featured", Boolean.class,
                    "status", Status.class
            );
        }

        @Override
        protected List<String> fillable() {
            return List.of("title", "published_on", "published_at", "views", "featured", "status");
        }
    }

    private enum Status {
        DRAFT,
        PUBLISHED
    }

    private static final class RecordingDatabase implements Database {
        private String table;
        private String whereColumn;
        private Object whereValue;
        private Map<String, Object> inserted = Map.of();
        private Map<String, Object> updated = Map.of();
        private boolean deleted;
        private Map<String, Object> firstRow = Map.of();
        private List<Map<String, Object>> rows = List.of();
        private QueryBuilder lastQuery;

        @Override
        public QueryBuilder table(String table) {
            this.table = table;
            this.lastQuery = new RecordingQueryBuilder();
            return lastQuery;
        }

        @Override
        public <T> T transaction(TransactionCallback<T> callback) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void close() {
        }

        private final class RecordingQueryBuilder implements QueryBuilder {
            @Override
            public QueryBuilder where(String column, Object value) {
                whereColumn = column;
                whereValue = value;
                return this;
            }

            @Override
            public Optional<Map<String, Object>> first() {
                return firstRow.isEmpty() ? Optional.empty() : Optional.of(new LinkedHashMap<>(firstRow));
            }

            @Override
            public List<Map<String, Object>> get() {
                return rows.stream().map(row -> (Map<String, Object>) new LinkedHashMap<>(row)).toList();
            }

            @Override
            public List<Map<String, Object>> paginate(int page, int perPage) {
                return List.of();
            }

            @Override
            public long insert(Map<String, Object> values) {
                inserted = new LinkedHashMap<>(values);
                return 41L;
            }

            @Override
            public int update(Map<String, Object> values) {
                updated = new LinkedHashMap<>(values);
                return 1;
            }

            @Override
            public int delete() {
                deleted = true;
                return 1;
            }
        }
    }
}
