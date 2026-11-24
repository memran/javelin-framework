package app.controllers;

import io.javelin.cache.Cache;
import io.javelin.core.Application;
import io.javelin.core.Json;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.queue.Job;
import io.javelin.core.queue.Queue;

import java.time.Duration;
import java.util.Map;

/** Endpoints used to exercise the cache and queue adapters against real backends. */
public final class BackendController {
    private final Cache cache;
    private final Application app;

    public BackendController(Cache cache, Application app) {
        this.cache = cache;
        this.app = app;
    }

    public Response cacheSet(Request request) {
        String value = request.query("value").orElse("hello");
        cache.put("demo:key", value, Duration.ofMinutes(5));
        return Json.ok(Map.of("stored", value));
    }

    public Response cacheGet(Request request) {
        return Json.ok(Map.of("value", cache.get("demo:key", String.class).orElse("")));
    }

    public Response publish(Request request) {
        if (!app.has(Queue.class)) {
            return Response.text("Queue is not configured", 503);
        }
        boolean failFirst = request.query("failFirst").map(Boolean::parseBoolean).orElse(false);
        Job job = Job.of("demo.message", Map.of(
                "message", request.query("message").orElse("hello-nats"),
                "failFirst", failFirst
        ));
        app.make(Queue.class).publish(job);
        return Json.ok(Map.of("id", job.id(), "subject", job.subject()));
    }
}
