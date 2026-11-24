package io.javelin.core;

import java.util.Optional;

/** Explicit session persistence contract. Implementations decide where state lives. */
public interface SessionStore extends AutoCloseable {
    Session create();

    Optional<Session> find(String id);

    void save(Session session);

    void remove(String id);

    @Override
    void close();
}
