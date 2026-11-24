package io.javelin.log.slf4j;

import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.StructuredLogger;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RequestLoggingMiddlewareTest {
    @Test
    void generatesIdsAddsHeadersAndClearsMdc() throws Exception {
        RecordingLogger logger = new RecordingLogger();
        RequestLoggingMiddleware middleware = new RequestLoggingMiddleware(logger, "X-Request-ID", "X-Correlation-ID");

        Response response = middleware.handle(request(Map.of()), ignored -> Response.text("ok", 200));

        assertNotNull(response.headers().get("X-Request-ID"));
        assertNotNull(response.headers().get("X-Correlation-ID"));
        assertEquals(response.headers().get("X-Request-ID"), response.headers().get("X-Correlation-ID"));
        assertEquals(1, logger.infos.size());
        assertTrue(logger.infos.get(0).contains("/health"));
        assertTrue(logger.contexts.get(0).containsKey("duration_ms"));
        assertNull(MDC.get("request_id"));
        assertNull(MDC.get("correlation_id"));
    }

    @Test
    void propagatesIncomingRequestAndCorrelationIds() throws Exception {
        RecordingLogger logger = new RecordingLogger();
        RequestLoggingMiddleware middleware = new RequestLoggingMiddleware(logger, "X-Request-ID", "X-Correlation-ID");

        Response response = middleware.handle(request(Map.of(
                        "X-Request-ID", List.of("req-1"),
                        "X-Correlation-ID", List.of("corr-1"))),
                ignored -> Response.text("ok", 200));

        assertEquals("req-1", response.headers().get("X-Request-ID"));
        assertEquals("corr-1", response.headers().get("X-Correlation-ID"));
        assertEquals("req-1", logger.contexts.get(0).get("request_id"));
        assertEquals("corr-1", logger.contexts.get(0).get("correlation_id"));
    }

    @Test
    void logsFailuresAndKeepsMdcClean() throws Exception {
        RecordingLogger logger = new RecordingLogger();
        RequestLoggingMiddleware middleware = new RequestLoggingMiddleware(logger, "X-Request-ID", "X-Correlation-ID");

        try {
            middleware.handle(request(Map.of()), ignored -> {
                throw new IllegalStateException("boom");
            });
        } catch (IllegalStateException expected) {
            assertEquals("boom", expected.getMessage());
        }

        assertEquals(1, logger.errors.size());
        assertNull(MDC.get("request_id"));
    }

    private static Request request(Map<String, List<String>> headers) {
        return new Request(HttpMethod.GET, "/health", headers, Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }

    private static final class RecordingLogger implements StructuredLogger {
        private final List<String> infos = new ArrayList<>();
        private final List<String> errors = new ArrayList<>();
        private final List<Map<String, ?>> contexts = new ArrayList<>();

        @Override public void debug(String message) { }
        @Override public void info(String message) { infos.add(message); }
        @Override public void warn(String message) { }
        @Override public void error(String message, Throwable throwable) { errors.add(message); }
        @Override public void info(String message, Map<String, ?> context) { infos.add(message); contexts.add(context); }
        @Override public void warn(String message, Map<String, ?> context) { }
        @Override public void error(String message, Throwable throwable, Map<String, ?> context) {
            errors.add(message);
            contexts.add(context);
        }
    }
}
