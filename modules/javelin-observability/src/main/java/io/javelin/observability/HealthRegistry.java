package io.javelin.observability;

import java.util.LinkedHashMap;
import java.util.Map;

/** Registry of liveness and readiness indicators. */
public final class HealthRegistry {
    private final Map<String, HealthIndicator> liveness = new LinkedHashMap<>();
    private final Map<String, HealthIndicator> readiness = new LinkedHashMap<>();

    public synchronized void live(String name, HealthIndicator indicator) {
        liveness.put(name, indicator);
    }

    public synchronized void ready(String name, HealthIndicator indicator) {
        readiness.put(name, indicator);
    }

    public synchronized Map<String, Health> liveness() {
        return check(liveness);
    }

    public synchronized Map<String, Health> readiness() {
        return check(readiness);
    }

    public boolean isReady() {
        return readiness().values().stream().allMatch(Health::healthy);
    }

    private static Map<String, Health> check(Map<String, HealthIndicator> indicators) {
        Map<String, Health> result = new LinkedHashMap<>();
        indicators.forEach((name, indicator) -> {
            try {
                result.put(name, indicator.check());
            } catch (RuntimeException exception) {
                result.put(name, Health.down(String.valueOf(exception)));
            }
        });
        return result;
    }
}
