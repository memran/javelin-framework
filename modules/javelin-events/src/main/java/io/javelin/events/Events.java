package io.javelin.events;

import java.util.Objects;

/** Static facade for the active event dispatcher. */
public final class Events {
    private static volatile EventDispatcher active;

    private Events() {
    }

    public static void use(EventDispatcher dispatcher) {
        active = Objects.requireNonNull(dispatcher, "dispatcher");
    }

    public static EventDispatcher active() {
        return active;
    }

    public static void dispatch(Object event) {
        active().dispatch(event);
    }

    public static <T> void listen(Class<T> eventType, EventListener<T> listener) {
        active().listen(eventType, listener);
    }

    public static <T> void listenQueued(Class<T> eventType, EventListener<T> listener) {
        active().listenQueued(eventType, listener);
    }
}
