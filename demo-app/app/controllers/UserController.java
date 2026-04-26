package app.controllers;

import io.javelin.core.Json;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.util.Map;

public final class UserController {
    public Response show(Request request) {
        return Json.ok(Map.of("id", request.param("id").orElse("")));
    }
}
