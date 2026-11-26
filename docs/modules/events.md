# Events Module

Artifact: `io.javelin:javelin-events`

`javelin-events` provides a small in-process dispatcher with synchronous and queue-backed listeners.

## Developer API

```java
import io.javelin.events.Events;

Events.listen(UserRegistered.class, event -> emailService.welcome(event.userId()));
Events.listenQueued(UserRegistered.class, event -> analyticsService.track(event.userId()));

Events.dispatch(new UserRegistered(userId));
```

Listeners are keyed by the concrete event class. Synchronous listeners run immediately on the dispatching thread; queued listeners are wrapped in a `QueuedEvent` job and published through `io.javelin.queues.Queue`.

## Queued listeners

`EventServiceProvider` registers a single handler for subject `queued.event` in the existing `JobHandlerRegistry`. When the queue worker consumes the job, the dispatcher deserializes the event and invokes only the listeners registered with `listenQueued`. This keeps the queue integration to one handler regardless of how many event types exist.

Queued listeners therefore share the queue's retry policy, timeout, failed-job storage and replay behavior. If no queue provider is registered, `listenQueued` will fail at dispatch time with a clear error.

## Service provider

`EventServiceProvider` binds `EventDispatcher`, activates the static `Events` facade during `boot()` and registers the queued-event job handler when a `JobHandlerRegistry` is available. Register it in `config.providers` (generated projects do this by default).

## Errors

A synchronous listener that throws is wrapped in `IllegalStateException` with the event type. Queued listener failures are handled by the queue worker and retried according to the registered `RetryPolicy`.
