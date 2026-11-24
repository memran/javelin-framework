package io.javelin.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Renders validation errors as a 422 response for HTML or JSON clients. */
public final class ValidationErrors {
    public static final int STATUS = 422;

    private ValidationErrors() {
    }

    public static JsonResponse json(Map<String, List<String>> errors) {
        return json(errors, STATUS);
    }

    public static JsonResponse json(Map<String, List<String>> errors, int status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Validation failed");
        body.put("status", status);
        body.put("errors", errors);
        return JsonResponse.of(status, body);
    }

    public static HtmlResponse html(Map<String, List<String>> errors) {
        return html(errors, STATUS);
    }

    public static HtmlResponse html(Map<String, List<String>> errors, int status) {
        StringBuilder list = new StringBuilder("<ul class=\"javelin-errors\">");
        errors.forEach((field, messages) -> messages.forEach(message -> list
                .append("<li>")
                .append(escape(field))
                .append(": ")
                .append(escape(message))
                .append("</li>")));
        return new HtmlResponse(list.append("</ul>").toString(), status);
    }

    /** Content-negotiated helper for controllers that serve both HTML and JSON. */
    public static Response of(Request request, Map<String, List<String>> errors) {
        boolean wantsJson = request.header("Accept")
                .map(value -> value.toLowerCase(java.util.Locale.ROOT).contains("application/json"))
                .orElse(false);
        return wantsJson ? json(errors) : html(errors);
    }

    private static String escape(String value) {
        String safe = value == null ? "" : value;
        return safe.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
