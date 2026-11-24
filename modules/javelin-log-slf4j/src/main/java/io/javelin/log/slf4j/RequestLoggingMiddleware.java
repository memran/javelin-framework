package io.javelin.log.slf4j;

import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.StructuredLogger;
import org.slf4j.MDC;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Assigns request and correlation IDs, records them in the SLF4J MDC and
 * response headers, and logs method, path, status and duration.
 */
public final class RequestLoggingMiddleware implements Middleware {
    static final String REQUEST_ID = "request_id";
    static final String CORRELATION_ID = "correlation_id";

    private final StructuredLogger logger;
    private final String requestIdHeader;
    private final String correlationIdHeader;

    public RequestLoggingMiddleware(StructuredLogger logger, String requestIdHeader, String correlationIdHeader) {
        this.logger = logger;
        this.requestIdHeader = requestIdHeader;
        this.correlationIdHeader = correlationIdHeader;
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        String requestId = fromHeaderOr(request, requestIdHeader, () -> UUID.randomUUID().toString());
        String correlationId = fromHeaderOr(request, correlationIdHeader, () -> requestId);
        MDC.put(REQUEST_ID, requestId);
        MDC.put(CORRELATION_ID, correlationId);
        long start = System.nanoTime();
        try {
            Response response = next.handle(request);
            long durationMs = millisSince(start);
            logger.info(request.method().name() + " " + request.path() + " " + response.status()
                            + " " + durationMs + "ms",
                    fields(request, requestId, correlationId, response.status(), durationMs));
            return withHeaderIfAbsent(
                    withHeaderIfAbsent(response, requestIdHeader, requestId),
                    correlationIdHeader, correlationId);
        } catch (Exception exception) {
            long durationMs = millisSince(start);
            logger.error(request.method().name() + " " + request.path() + " failed after "
                            + durationMs + "ms", exception,
                    fields(request, requestId, correlationId, null, durationMs));
            throw exception;
        } finally {
            MDC.remove(REQUEST_ID);
            MDC.remove(CORRELATION_ID);
        }
    }

    private static String fromHeaderOr(Request request, String header, java.util.function.Supplier<String> fallback) {
        return request.header(header)
                .filter(value -> !value.isBlank())
                .orElseGet(fallback);
    }

    private static long millisSince(long start) {
        return (System.nanoTime() - start) / 1_000_000;
    }

    private static Map<String, Object> fields(Request request, String requestId, String correlationId,
                                              Integer status, long durationMs) {
        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put(REQUEST_ID, requestId);
        fields.put(CORRELATION_ID, correlationId);
        fields.put("method", request.method().name());
        fields.put("path", request.path());
        if (status != null) {
            fields.put("status", status);
        }
        fields.put("duration_ms", durationMs);
        return fields;
    }

    private static Response withHeaderIfAbsent(Response response, String name, String value) {
        boolean present = response.headers().keySet().stream().anyMatch(key -> key.equalsIgnoreCase(name));
        return present ? response : response.withHeader(name, value);
    }
}
