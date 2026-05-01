package io.javelin.core;

public final class DefaultExceptionHandler implements ExceptionHandler {
    @Override
    public Response handle(Throwable throwable, Request request) {
        if (wantsJson(request)) {
            return JsonResponse.error("Internal Server Error", 500);
        }
        return Response.errorPage(500, "Internal Server Error", "An unexpected error occurred.");
    }

    private static boolean wantsJson(Request request) {
        return request.header("Accept")
                .map(value -> value.toLowerCase().contains("application/json"))
                .orElse(false);
    }
}
