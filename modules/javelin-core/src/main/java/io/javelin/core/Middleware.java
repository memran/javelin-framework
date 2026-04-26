package io.javelin.core;

@FunctionalInterface
public interface Middleware {
    Response handle(Request request, Next next) throws Exception;

    @FunctionalInterface
    interface Next {
        Response handle(Request request) throws Exception;
    }
}
