package io.javelin.core;

@FunctionalInterface
public interface RouteHandler {
    Response handle(Request request) throws Exception;
}
