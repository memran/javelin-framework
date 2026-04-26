package io.javelin.security;

import io.javelin.core.JsonResponse;
import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class RateLimitMiddleware implements Middleware {
    private final int maxRequests;
    private final long windowMillis;
    private final Clock clock;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public RateLimitMiddleware(int maxRequests, long windowMillis) {
        this(maxRequests, windowMillis, Clock.systemUTC());
    }

    RateLimitMiddleware(int maxRequests, long windowMillis, Clock clock) {
        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
        this.clock = clock;
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        Bucket bucket = buckets.compute(request.remoteAddress(), (ignored, current) -> current == null || expired(current)
                ? new Bucket(clock.millis(), new AtomicInteger())
                : current);
        if (bucket.count().incrementAndGet() > maxRequests) {
            return JsonResponse.error("Too Many Requests", 429);
        }
        return next.handle(request);
    }

    private boolean expired(Bucket bucket) {
        return clock.millis() - bucket.startedAt() >= windowMillis;
    }

    private record Bucket(long startedAt, AtomicInteger count) {
    }
}
