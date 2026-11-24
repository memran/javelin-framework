package app.controllers;

import io.javelin.core.Application;
import io.javelin.core.HtmlResponse;
import io.javelin.core.Json;
import io.javelin.core.JsonResponse;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.core.Session;
import io.javelin.core.ValidationErrors;
import io.javelin.core.security.Authenticated;
import io.javelin.core.security.BearerAuthMiddleware;
import io.javelin.security.CsrfMiddleware;
import io.javelin.security.JwtTokenIssuer;
import io.javelin.validation.Validator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Runnable examples for the security tutorial. Demo credentials: alice / secret. */
public final class SecurityExampleController {
    private final Application app;
    private final Validator validator;

    public SecurityExampleController(Application app, Validator validator) {
        this.app = app;
        this.validator = validator;
    }

    public HtmlResponse loginForm(Request request) {
        String token = request.attribute(CsrfMiddleware.TOKEN_KEY)
                .map(Object::toString)
                .orElseGet(() -> request.session().map(session -> session.get(CsrfMiddleware.TOKEN_KEY))
                        .map(Object::toString).orElse(""));
        String html = """
                <!doctype html>
                <html lang="en"><head><meta charset="utf-8"><title>Sign in</title></head>
                <body>
                  <h1>Sign in</h1>
                  <form method="post" action="/demo/session/login">
                    <input type="hidden" name="_token" value="%s">
                    <label>Username <input name="username"></label>
                    <label>Password <input type="password" name="password"></label>
                    <button type="submit">Sign in</button>
                  </form>
                </body></html>
                """.formatted(escape(token));
        return new HtmlResponse(html);
    }

    public Response login(Request request) {
        Map<String, String> values = Map.of(
                "username", request.input().text("username").orElse(""),
                "password", request.input().text("password").orElse(""));
        Map<String, List<String>> errors = validator.validate(values, Map.of(
                "username", List.of("required"),
                "password", List.of("required", "min:6")));
        if (!errors.isEmpty()) {
            return ValidationErrors.of(request, errors);
        }
        if (!"alice".equals(values.get("username")) || !"secret".equals(values.get("password"))) {
            return ValidationErrors.json(Map.of("credentials", List.of("Invalid username or password")));
        }
        request.session().ifPresent(session ->
                session.put("javelin.user", new Authenticated("alice", Set.of("users.read"))));
        return Response.redirect("/demo/session/me");
    }

    public JsonResponse me(Request request) {
        Authenticated principal = request.attribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE)
                .filter(Authenticated.class::isInstance)
                .map(Authenticated.class::cast)
                .orElseThrow();
        return Json.ok(Map.of("subject", principal.subject(), "permissions", principal.permissions()));
    }

    public Response logout(Request request) {
        Optional<Session> session = request.session();
        session.ifPresent(Session::invalidate);
        return Response.redirect("/demo/session/login");
    }

    public JsonResponse token(Request request) {
        JwtTokenIssuer issuer = app.make(JwtTokenIssuer.class);
        return Json.ok(Map.of("token", issuer.issue("alice", Set.of("users.read")),
                "token_type", "Bearer"));
    }

    private static String escape(String value) {
        return value.replace("&", "&amp;").replace("\"", "&quot;")
                .replace("<", "&lt;").replace(">", "&gt;");
    }
}
