package app.controllers;

import io.javelin.core.Json;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.View;

import java.util.Map;

public final class HomeController {
    public Response index(Request request) {
        return View.render("users/index", Map.of("name", "Javelin"));
    }

    public Response health(Request request) {
        return Json.ok(Map.of("status", "ok"));
    }
}
