package app.controllers;

import app.jobs.InvoiceJob;
import app.jobs.SendEmailJob;
import io.javelin.core.Json;
import io.javelin.core.JsonResponse;
import io.javelin.core.Request;
import io.javelin.queue.JobQueue;
import io.javelin.queues.Queue;

import java.time.Duration;
import java.util.Map;

/** Runnable examples for docs/tutorials/background-jobs.md. */
public final class QueueExampleController {
    public JsonResponse dispatch(Request request) {
        String userId = request.query("user").orElse("42");
        Queue.dispatch(new SendEmailJob(userId));
        return Json.ok(Map.of("status", "queued", "job", "send.email"));
    }

    public JsonResponse later(Request request) {
        String id = request.query("id").orElse("INV-1");
        Queue.later(new InvoiceJob(id), Duration.ofMinutes(1));
        return Json.ok(Map.of("status", "scheduled", "job", "invoice.due"));
    }

    public JsonResponse status(Request request) {
        JobQueue queue = Queue.active();
        return Json.ok(Map.of(
                "backend", queue.getClass().getSimpleName(),
                "pending", queue.pendingCount(),
                "failed", queue.failedJobs().size()));
    }
}
