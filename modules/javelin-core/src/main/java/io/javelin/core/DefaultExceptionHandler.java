package io.javelin.core;

public final class DefaultExceptionHandler implements ExceptionHandler {
    @Override
    public Response handle(Throwable throwable, Request request) {
        return JsonResponse.error("Internal Server Error", 500);
    }
}
