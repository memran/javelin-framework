package app.jobs;

import io.javelin.scheduler.ScheduledTask;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Example scheduled task used by the demo endpoints. */
public final class DemoScheduleJob implements ScheduledTask {
    @Override
    public void run() throws Exception {
        Path marker = Path.of("storage", "automation", "scheduled.txt");
        Files.createDirectories(marker.getParent());
        Files.writeString(marker, "ran at " + System.currentTimeMillis() + "\n",
                StandardCharsets.UTF_8, java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.APPEND);
    }
}
