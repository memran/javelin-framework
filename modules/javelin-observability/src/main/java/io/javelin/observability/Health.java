package io.javelin.observability;

import java.util.LinkedHashMap;
import java.util.Map;

/** Result of one health or readiness check. */
public record Health(String status, Map<String, Object> details) {
    public static final String UP = "up";
    public static final String DOWN = "down";

    public static Health up() {
        return new Health(UP, Map.of());
    }

    public static Health up(Map<String, Object> details) {
        return new Health(UP, Map.copyOf(details));
    }

    public static Health down(String reason) {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("reason", reason);
        return new Health(DOWN, Map.copyOf(details));
    }

    public boolean healthy() {
        return UP.equals(status);
    }
}
