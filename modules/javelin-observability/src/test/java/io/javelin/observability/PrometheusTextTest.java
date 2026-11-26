package io.javelin.observability;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PrometheusTextTest {
    @Test
    void rendersTypedSanitizedMetricsWithPoolsAndSkipsNonNumericValues() {
        Map<String, Object> metrics = new LinkedHashMap<>();
        metrics.put("http.requests", 5L);
        metrics.put("http.response.200", 4L);
        metrics.put("queue.depth", 7);
        Map<String, Object> runtime = Map.of("heap_used_mb", 23L, "started_at", "2026-11-26T12:00:00Z");
        Map<String, Object> startup = Map.of("stage_boot_ms", 1L);
        Map<String, Object> pools = Map.of("primary", Map.of("active", 2, "max", 10));

        String text = PrometheusText.render(metrics,
                Set.of("http.requests", "http.response.200"), runtime, startup, pools);

        assertTrue(text.contains("# TYPE javelin_http_requests counter"));
        assertTrue(text.contains("javelin_http_requests 5"));
        assertTrue(text.contains("javelin_http_response_200 4"));
        assertTrue(text.contains("# TYPE javelin_queue_depth gauge"));
        assertTrue(text.contains("javelin_queue_depth 7"));
        assertTrue(text.contains("javelin_runtime_heap_used_mb 23"));
        assertFalse(text.contains("javelin_runtime_started_at "));
        assertTrue(text.contains("javelin_startup_stage_boot_ms 1"));
        assertTrue(text.contains("javelin_db_pool_active{pool=\"primary\"} 2"));
        assertTrue(text.contains("javelin_db_pool_max{pool=\"primary\"} 10"));
        assertEquals(1, text.lines().filter(line -> line.startsWith("# TYPE javelin_db_pool_active")).count());
    }
}
