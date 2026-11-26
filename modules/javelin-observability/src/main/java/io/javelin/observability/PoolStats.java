package io.javelin.observability;

import java.util.LinkedHashMap;
import java.util.Map;

/** Connection pool snapshot. */
public record PoolStats(String name, int active, int idle, int total, int max) {
    public Map<String, Object> toMap() {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("active", active);
        values.put("idle", idle);
        values.put("total", total);
        values.put("max", max);
        return values;
    }
}
