package io.javelin.core;

import io.javelin.support.Str;
import io.javelin.support.Date;

import java.util.Collections;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Locale;

public abstract class Model {
    private final Database database;
    private final Map<String, Object> attributes = new LinkedHashMap<>();
    private Long id;

    protected Model(Database database) {
        this(database, null);
    }

    protected Model(Database database, Long id) {
        this.database = Objects.requireNonNull(database, "database");
        this.id = id;
    }

    public final Optional<Long> id() {
        return Optional.ofNullable(id);
    }

    public final boolean exists() {
        return id != null;
    }

    public final String table() {
        return tableName();
    }

    public final long save() {
        Map<String, Object> data = new LinkedHashMap<>(Objects.requireNonNull(attributes(), "attributes"));
        data.remove(primaryKey());
        if (id == null) {
            long generated = database.table(table()).insert(data);
            id = generated;
            return generated;
        }
        database.table(table())
                .where(primaryKey(), id)
                .update(data);
        return id;
    }

    public final int delete() {
        if (id == null) {
            return 0;
        }
        return database.table(table())
                .where(primaryKey(), id)
                .delete();
    }

    public final Map<String, Object> toMap() {
        Map<String, Object> values = new LinkedHashMap<>(Objects.requireNonNull(attributes(), "attributes"));
        id().ifPresent(value -> values.put(primaryKey(), value));
        return Collections.unmodifiableMap(values);
    }

    public final Optional<Object> attribute(String key) {
        Objects.requireNonNull(key, "key");
        return Optional.ofNullable(attributes().get(key));
    }

    public final Model fill(Map<String, Object> values) {
        assignAttributes(values, false);
        return this;
    }

    public final Model forceFill(Map<String, Object> values) {
        assignAttributes(values, true);
        return this;
    }

    public static <T extends Model> Optional<T> find(Database database, Class<T> type, long id) {
        Objects.requireNonNull(database, "database");
        Objects.requireNonNull(type, "type");
        Map<String, Object> row = database.table(defaultTableName(type))
                .where("id", id)
                .first()
                .orElse(null);
        if (row == null) {
            return Optional.empty();
        }
        return Optional.of(hydrate(database, type, id, row));
    }

    public static <T extends Model> T findOrFail(Database database, Class<T> type, long id) {
        return find(database, type, id)
                .orElseThrow(() -> new IllegalStateException(type.getSimpleName() + " not found for id " + id));
    }

    public static <T extends Model> QueryBuilder query(Database database, Class<T> type) {
        Objects.requireNonNull(database, "database");
        Objects.requireNonNull(type, "type");
        return database.table(defaultTableName(type));
    }

    public static <T extends Model> QueryBuilder where(Database database, Class<T> type, String column, Object value) {
        return query(database, type).where(column, value);
    }

    public static <T extends Model> Optional<T> firstWhere(Database database, Class<T> type, String column, Object value) {
        Objects.requireNonNull(column, "column");
        return query(database, type)
                .where(column, value)
                .first()
                .map(row -> hydrate(database, type, idOf(row), row));
    }

    public static <T extends Model> List<T> all(Database database, Class<T> type) {
        Objects.requireNonNull(database, "database");
        Objects.requireNonNull(type, "type");
        List<T> models = new ArrayList<>();
        for (Map<String, Object> row : database.table(defaultTableName(type)).get()) {
            Object identifier = row.get("id");
            long id = identifier instanceof Number number ? number.longValue() : 0L;
            models.add(hydrate(database, type, id, row));
        }
        return List.copyOf(models);
    }

    protected final Database database() {
        return database;
    }

    protected void assignId(long id) {
        this.id = id;
    }

    protected String tableName() {
        return defaultTableName(getClass());
    }

    protected String primaryKey() {
        return "id";
    }

    protected Map<String, Object> attributes() {
        return attributes;
    }

    protected List<String> fillable() {
        return List.of();
    }

    protected List<String> guarded() {
        return List.of(primaryKey());
    }

    protected Map<String, Class<?>> casts() {
        return Map.of();
    }

    public static String defaultTableName(Class<?> type) {
        Objects.requireNonNull(type, "type");
        String table = Str.toSnakeCase(type.getSimpleName());
        if (table.endsWith("y") && table.length() > 1 && !isVowel(table.charAt(table.length() - 2))) {
            return table.substring(0, table.length() - 1) + "ies";
        }
        if (table.endsWith("s") || table.endsWith("x") || table.endsWith("z") || table.endsWith("ch") || table.endsWith("sh")) {
            return table + "es";
        }
        return table + "s";
    }

    private static boolean isVowel(char value) {
        return switch (Character.toLowerCase(value)) {
            case 'a', 'e', 'i', 'o', 'u' -> true;
            default -> false;
        };
    }

    private static <T extends Model> T hydrate(Database database, Class<T> type, long id, Map<String, Object> row) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor(Database.class);
            constructor.setAccessible(true);
            T model = constructor.newInstance(database);
            model.assignId(id);
            model.forceFill(row);
            return model;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to instantiate model " + type.getName(), exception);
        }
    }

    private static long idOf(Map<String, Object> row) {
        Object identifier = row.get("id");
        if (identifier instanceof Number number) {
            return number.longValue();
        }
        if (identifier instanceof String text && !text.isBlank()) {
            try {
                return Long.parseLong(text);
            } catch (NumberFormatException exception) {
                throw new IllegalStateException("Invalid model id: " + text, exception);
            }
        }
        throw new IllegalStateException("Model row is missing a valid id");
    }

    private void assignAttributes(Map<String, Object> values, boolean force) {
        if (!force) {
            attributes.clear();
        }
        if (values == null || values.isEmpty()) {
            return;
        }
        List<String> fillable = fillable();
        List<String> guarded = guarded();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String key = Objects.requireNonNull(entry.getKey(), "key");
            if (primaryKey().equals(key)) {
                continue;
            }
            if (!force) {
                if (!fillable.isEmpty() && !fillable.contains(key)) {
                    continue;
                }
                if (guarded.contains(key)) {
                    continue;
                }
            }
            attributes.put(key, castValue(key, entry.getValue()));
        }
    }

    private Object castValue(String key, Object value) {
        if (value == null) {
            return null;
        }
        Class<?> target = casts().get(key);
        if (target == null || target.isInstance(value)) {
            return value;
        }
        if (target == String.class) {
            return value.toString();
        }
        if (target == Integer.class || target == int.class) {
            return toInteger(value, key);
        }
        if (target == Long.class || target == long.class) {
            return toLong(value, key);
        }
        if (target == Boolean.class || target == boolean.class) {
            return toBoolean(value, key);
        }
        if (target == LocalDate.class) {
            return toLocalDate(value, key);
        }
        if (target == LocalDateTime.class) {
            return toLocalDateTime(value, key);
        }
        if (target.isEnum()) {
            return toEnum(target, value, key);
        }
        return value;
    }

    private Integer toInteger(Object value, String key) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        if (value instanceof String text) {
            try {
                return Integer.valueOf(text.trim());
            } catch (NumberFormatException exception) {
                throw new IllegalStateException("Unable to cast " + key + " to Integer", exception);
            }
        }
        throw new IllegalStateException("Unable to cast " + key + " to Integer");
    }

    private Long toLong(Object value, String key) {
        if (value instanceof Number number) {
            return number.longValue();
        }
        if (value instanceof String text) {
            try {
                return Long.valueOf(text.trim());
            } catch (NumberFormatException exception) {
                throw new IllegalStateException("Unable to cast " + key + " to Long", exception);
            }
        }
        throw new IllegalStateException("Unable to cast " + key + " to Long");
    }

    private Boolean toBoolean(Object value, String key) {
        if (value instanceof Boolean bool) {
            return bool;
        }
        if (value instanceof String text) {
            String normalized = text.trim().toLowerCase(java.util.Locale.ROOT);
            return switch (normalized) {
                case "1", "true", "yes", "on" -> true;
                case "0", "false", "no", "off" -> false;
                default -> throw new IllegalStateException("Unable to cast " + key + " to Boolean");
            };
        }
        if (value instanceof Number number) {
            return number.intValue() != 0;
        }
        throw new IllegalStateException("Unable to cast " + key + " to Boolean");
    }

    private LocalDate toLocalDate(Object value, String key) {
        if (value instanceof LocalDate localDate) {
            return localDate;
        }
        if (value instanceof String text) {
            return Date.requireDate(text, "Unable to cast " + key + " to LocalDate");
        }
        throw new IllegalStateException("Unable to cast " + key + " to LocalDate");
    }

    private LocalDateTime toLocalDateTime(Object value, String key) {
        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime;
        }
        if (value instanceof String text) {
            return Date.requireDateTime(text, "Unable to cast " + key + " to LocalDateTime");
        }
        throw new IllegalStateException("Unable to cast " + key + " to LocalDateTime");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Enum<?> toEnum(Class<?> target, Object value, String key) {
        Class<? extends Enum> enumType = (Class<? extends Enum>) target;
        if (enumType.isInstance(value)) {
            return (Enum<?>) value;
        }
        if (value instanceof String text) {
            String candidate = text.trim();
            if (candidate.isEmpty()) {
                throw new IllegalStateException("Unable to cast " + key + " to " + target.getSimpleName());
            }
            String normalized = candidate.toUpperCase(Locale.ROOT);
            for (Enum constant : enumType.getEnumConstants()) {
                if (constant.name().equalsIgnoreCase(candidate) || constant.name().equals(normalized)) {
                    return constant;
                }
            }
        }
        throw new IllegalStateException("Unable to cast " + key + " to " + target.getSimpleName());
    }
}
