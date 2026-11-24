package app.providers;

import app.views.JavelinViewExtension;
import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.javelin.core.queue.Job;
import io.javelin.core.queue.Queue;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.view.pebble.PebbleViewExtensions;
import routes.web;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Map;

public final class AppServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.make(PebbleViewExtensions.class)
                .extension(new JavelinViewExtension())
                .global("javelinRuntime", "JDK 25");
    }

    @Override
    public void boot(Application app) {
        web.register(app);
        if (!app.has(Queue.class)) {
            return;
        }
        app.make(Queue.class).subscribe("demo.message", job -> {
            Map<?, ?> payload = job.payload(Map.class);
            if (Boolean.TRUE.equals(payload.get("failFirst")) && job.attempts() < 2) {
                throw new IllegalStateException("Simulated failure on attempt " + job.attempts());
            }
            Path marker = Path.of("storage", "queue-received.txt");
            Files.createDirectories(marker.getParent());
            Files.writeString(marker,
                    "attempts=" + job.attempts() + " message=" + payload.get("message") + "\n",
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }, new RetryPolicy(3, Duration.ofMillis(200)));
    }
}
