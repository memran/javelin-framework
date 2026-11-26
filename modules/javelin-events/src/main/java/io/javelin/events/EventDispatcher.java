package io.javelin.events;

import java.util.List;
import java.util.Optional;

/** Dispatches events to synchronous and queued listeners. */
public interface EventDispatcher {
    <T> void listen(Class<T> eventType, EventListener<T> listener);

    <T> void listenQueued(Class<T> eventType, EventListener<T> listener);

    void dispatch(Object event);

    /** Invokes the queued listeners for a deserialized event. Used by the queue handler. */
    void dispatchQueued(String eventType, String payload);

    List<Class<?>> eventTypes();
}
