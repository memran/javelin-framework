package io.javelin.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DefaultExceptionHandler implements ExceptionHandler {
    private final boolean detailed;

    public DefaultExceptionHandler() {
        this(false);
    }

    public DefaultExceptionHandler(boolean detailed) {
        this.detailed = detailed;
    }

    @Override
    public Response handle(Throwable throwable, Request request) {
        if (detailed) {
            return wantsJson(request) ? detailedJson(throwable) : detailedHtml(throwable);
        }
        return wantsJson(request)
                ? JsonResponse.error("Internal Server Error", 500)
                : Response.errorPage(500, "Internal Server Error", "An unexpected error occurred.");
    }

    private static Response detailedJson(Throwable throwable) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Internal Server Error");
        body.put("status", 500);
        body.put("message", String.valueOf(throwable.getMessage()));
        body.put("type", throwable.getClass().getName());
        body.put("trace", stackTrace(throwable));
        return JsonResponse.of(500, body);
    }

    private static Response detailedHtml(Throwable throwable) {
        String trace = escapeHtml(stackTrace(throwable));
        String type = escapeHtml(throwable.getClass().getName());
        String message = escapeHtml(String.valueOf(throwable.getMessage()));
        String body = """
                <!doctype html>
                <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <title>Internal Server Error</title>
                    <style>
                        body { font-family: system-ui, sans-serif; margin: 2rem; color: #1f2937; }
                        pre { background: #f6f7f9; padding: 1rem; overflow-x: auto; }
                    </style>
                </head>
                <body>
                    <h1>Internal Server Error</h1>
                    <p><strong>%s</strong>: %s</p>
                    <pre>%s</pre>
                </body>
                </html>
                """.formatted(type, message, trace);
        return new HtmlResponse(body, 500);
    }

    private static String stackTrace(Throwable throwable) {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    private static String escapeHtml(String value) {
        StringBuilder builder = new StringBuilder(value.length());
        for (char character : value.toCharArray()) {
            switch (character) {
                case '&' -> builder.append("&amp;");
                case '<' -> builder.append("&lt;");
                case '>' -> builder.append("&gt;");
                case '"' -> builder.append("&quot;");
                case '\'' -> builder.append("&#39;");
                default -> builder.append(character);
            }
        }
        return builder.toString();
    }

    private static boolean wantsJson(Request request) {
        return request.header("Accept")
                .map(value -> value.toLowerCase().contains("application/json"))
                .orElse(false);
    }
}
