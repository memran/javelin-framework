package io.javelin.core;

public final class Json {
    private Json() {
    }

    public static JsonResponse ok(Object data) {
        return JsonResponse.ok(data);
    }

    public static JsonResponse error(String message, int status) {
        return JsonResponse.error(message, status);
    }
}
