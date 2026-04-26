package io.javelin.db.jdbc;

import io.javelin.core.QueryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

final class JdbcQueryBuilder implements QueryBuilder {
    private final JdbcDatabase database;
    private final String table;
    private final List<Condition> conditions = new ArrayList<>();

    JdbcQueryBuilder(JdbcDatabase database, String table) {
        this.database = database;
        this.table = table;
    }

    @Override
    public QueryBuilder where(String column, Object value) {
        conditions.add(new Condition(column, value));
        return this;
    }

    @Override
    public Optional<Map<String, Object>> first() {
        List<Map<String, Object>> rows = select(" limit 1", List.of());
        return rows.stream().findFirst();
    }

    @Override
    public List<Map<String, Object>> get() {
        return select("", List.of());
    }

    @Override
    public List<Map<String, Object>> paginate(int page, int perPage) {
        int offset = Math.max(0, page - 1) * perPage;
        return select(" limit ? offset ?", List.of(perPage, offset));
    }

    @Override
    public long insert(Map<String, Object> values) {
        StringJoiner columns = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");
        values.keySet().forEach(column -> {
            columns.add(column);
            placeholders.add("?");
        });
        String sql = "insert into " + table + " (" + columns + ") values (" + placeholders + ")";
        Connection connection = null;
        try {
            connection = database.connection();
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                bind(statement, new ArrayList<>(values.values()));
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    return keys.next() ? keys.getLong(1) : 0;
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Insert failed", exception);
        } finally {
            closeIfNeeded(connection);
        }
    }

    @Override
    public int update(Map<String, Object> values) {
        StringJoiner sets = new StringJoiner(", ");
        values.keySet().forEach(column -> sets.add(column + " = ?"));
        List<Object> bindings = new ArrayList<>(values.values());
        bindings.addAll(conditionValues());
        return execute("update " + table + " set " + sets + whereClause(), bindings);
    }

    @Override
    public int delete() {
        return execute("delete from " + table + whereClause(), conditionValues());
    }

    private List<Map<String, Object>> select(String suffix, List<Object> extraBindings) {
        List<Object> bindings = conditionValues();
        bindings.addAll(extraBindings);
        String sql = "select * from " + table + whereClause() + suffix;
        Connection connection = null;
        try {
            connection = database.connection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                bind(statement, bindings);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return rows(resultSet);
                }
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Select failed", exception);
        } finally {
            closeIfNeeded(connection);
        }
    }

    private int execute(String sql, List<Object> bindings) {
        Connection connection = null;
        try {
            connection = database.connection();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                bind(statement, bindings);
                return statement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Statement failed", exception);
        } finally {
            closeIfNeeded(connection);
        }
    }

    private void closeIfNeeded(Connection connection) {
        if (connection != null && !database.inTransaction()) {
            try {
                connection.close();
            } catch (java.sql.SQLException exception) {
                throw new IllegalStateException("Unable to close connection", exception);
            }
        }
    }

    private String whereClause() {
        if (conditions.isEmpty()) {
            return "";
        }
        StringJoiner joiner = new StringJoiner(" and ", " where ", "");
        conditions.forEach(condition -> joiner.add(condition.column() + " = ?"));
        return joiner.toString();
    }

    private List<Object> conditionValues() {
        return conditions.stream().map(Condition::value).collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    private static void bind(PreparedStatement statement, List<Object> bindings) throws java.sql.SQLException {
        for (int i = 0; i < bindings.size(); i++) {
            statement.setObject(i + 1, bindings.get(i));
        }
    }

    private static List<Map<String, Object>> rows(ResultSet resultSet) throws java.sql.SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        ResultSetMetaData meta = resultSet.getMetaData();
        while (resultSet.next()) {
            Map<String, Object> row = new LinkedHashMap<>();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                row.put(meta.getColumnLabel(i), resultSet.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }

    private record Condition(String column, Object value) {
    }
}
