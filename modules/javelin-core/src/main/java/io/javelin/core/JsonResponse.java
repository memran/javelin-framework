package io.javelin.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public final class JsonResponse extends Response {
    private static final ObjectMapper JSON = new ObjectMapper();

    private JsonResponse(int status, Object data) {
        super(status, Map.of("Content-Type", "application/json; charset=utf-8"), serialize(data));
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

    private static byte[] serialize(Object data) {
        try {
            return JSON.writeValueAsBytes(data);
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("Unable to serialize JSON response", exception);
        }
    }
}
