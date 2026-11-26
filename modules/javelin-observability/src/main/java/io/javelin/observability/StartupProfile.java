package io.javelin.observability;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Startup timings available through the metrics endpoint. */
public final class StartupProfile {
    private static final Instant STARTED = Instant.now();
    private static final Map<String, Long> STAGES = new ConcurrentHashMap<>();

    private StartupProfile() {
    }

    public static void stage(String name) {
        STAGES.put(name, Duration.between(STARTED, Instant.now()).toMillis());
    }

    public static Map<String, Object> snapshot() {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("started_at", STARTED.toString());
        values.put("uptime_ms", Duration.between(STARTED, Instant.now()).toMillis());
        STAGES.forEach((name, millis) -> values.put("stage_" + name + "_ms", millis));
        return values;
    }
}
