package io.javelin.core;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface QueryBuilder {
    QueryBuilder where(String column, Object value);

    Optional<Map<String, Object>> first();

    List<Map<String, Object>> get();

    List<Map<String, Object>> paginate(int page, int perPage);

    long insert(Map<String, Object> values);

    int update(Map<String, Object> values);

    int delete();
}
