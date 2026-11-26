package app.controllers;

import app.events.UserRegisteredEvent;
import app.jobs.DemoScheduleJob;
import io.javelin.core.Json;
import io.javelin.core.JsonResponse;
import io.javelin.core.Request;
import io.javelin.events.Events;
import io.javelin.scheduler.Schedules;
import io.javelin.scheduler.TaskDefinition;
import io.javelin.schedules.Schedule;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

/** Runnable examples for docs/tutorials/scheduling-and-events.md. */
public final class AutomationExampleController {
    public JsonResponse schedule(Request request) {
        Schedule.active().register(new TaskDefinition("demo-schedule",
                new Schedules.Interval(Duration.ZERO), new DemoScheduleJob(), false));
        int started = Schedule.active().runDue();
        return Json.ok(Map.of("started", started, "history", Schedule.history(5).size()));
    }

    public JsonResponse event(Request request) {
        String user = request.query("user").orElse("alice");
        Events.listen(UserRegisteredEvent.class, event -> {
            Path marker = Path.of("storage", "automation", "user-registered.txt");
            Files.createDirectories(marker.getParent());
            Files.writeString(marker, event.userId() + "\n", StandardCharsets.UTF_8,
                    java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        });
        Events.dispatch(new UserRegisteredEvent(user));
        return Json.ok(Map.of("dispatched", user));
    }
}
