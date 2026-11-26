package io.javelin.events;

/** Envelope used to deliver an event through the queue. */
public record QueuedEvent(String eventType, String payload) {
}
