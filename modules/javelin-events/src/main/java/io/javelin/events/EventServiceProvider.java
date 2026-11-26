package io.javelin.events;

import io.javelin.core.Application;
import io.javelin.core.Json;
import io.javelin.core.ServiceProvider;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.queue.JobHandlerRegistry;

import java.time.Duration;

/** Wires the dispatcher and registers the queued-event job handler. */
public final class EventServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(EventDispatcher.class, DefaultEventDispatcher::new);
    }

    @Override
    public void boot(Application app) {
        EventDispatcher dispatcher = app.make(EventDispatcher.class);
        Events.use(dispatcher);
        if (app.has(JobHandlerRegistry.class)) {
            app.make(JobHandlerRegistry.class).register("queued.event", job -> {
                QueuedEvent envelope = Json.parse(job.payload(), QueuedEvent.class);
                dispatcher.dispatchQueued(envelope.eventType(), envelope.payload());
            }, new RetryPolicy(3, Duration.ofSeconds(1)));
        }
    }
}
