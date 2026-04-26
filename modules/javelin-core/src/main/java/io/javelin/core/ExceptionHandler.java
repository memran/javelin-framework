package io.javelin.core;

public interface ExceptionHandler {
    Response handle(Throwable throwable, Request request);
}
