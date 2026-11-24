package io.javelin.core;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class JsonResponse extends Response {
    private JsonResponse(int status, Object data) {
        super(status, Map.of("Content-Type", "application/json; charset=utf-8"),
                Json.stringify(data).getBytes(StandardCharsets.UTF_8));
    }

    public static JsonResponse ok(Object data) {
        return new JsonResponse(200, data);
    }

    public static JsonResponse of(int status, Object data) {
        return new JsonResponse(status, data);
    }

    public static JsonResponse error(String message, int status) {
        return new JsonResponse(status, Map.of("error", message, "status", status));
    }
}
