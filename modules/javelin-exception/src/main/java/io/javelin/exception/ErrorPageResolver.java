package io.javelin.exception;

import io.javelin.core.Request;
import io.javelin.core.Response;

import java.util.Optional;

/** Resolves a custom error response for a status code, if one is configured. */
@FunctionalInterface
public interface ErrorPageResolver {
    Optional<Response> resolve(int status, Request request);
}
