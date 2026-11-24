package io.javelin.exception;

import io.javelin.core.ExceptionHandler;
import io.javelin.core.HtmlResponse;
import io.javelin.core.JsonResponse;
import io.javelin.core.Request;
import io.javelin.core.Response;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Global exception handler with production defaults: generic responses, custom
 * pages from disk, and redacted messages. Detailed mode is for development only.
 */
public final class ConfigurableExceptionHandler implements ExceptionHandler {
    private final boolean detailed;
    private final SensitiveDataRedactor redactor;
    private final ErrorPageResolver pages;

    public ConfigurableExceptionHandler(boolean detailed, SensitiveDataRedactor redactor, ErrorPageResolver pages) {
        this.detailed = detailed;
        this.redactor = Objects.requireNonNull(redactor, "redactor");
        this.pages = Objects.requireNonNull(pages, "pages");
    }

    @Override
    public Response handle(Throwable throwable, Request request) {
        if (detailed) {
            return wantsJson(request) ? detailedJson(throwable) : detailedHtml(throwable);
        }
        Optional<Response> page = pages.resolve(500, request);
        if (page.isPresent()) {
            return page.get();
        }
        return wantsJson(request)
                ? JsonResponse.error("Internal Server Error", 500)
                : Response.errorPage(500, "Internal Server Error", "An unexpected error occurred.");
    }

    private Response detailedJson(Throwable throwable) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "Internal Server Error");
        body.put("status", 500);
        body.put("message", redactor.redact(String.valueOf(throwable.getMessage())));
        body.put("type", throwable.getClass().getName());
        body.put("trace", redactor.redact(stackTrace(throwable)));
        return JsonResponse.of(500, body);
    }

    private Response detailedHtml(Throwable throwable) {
        String message = escapeHtml(redactor.redact(String.valueOf(throwable.getMessage())));
        String type = escapeHtml(throwable.getClass().getName());
        String trace = escapeHtml(redactor.redact(stackTrace(throwable)));
        String body = """
                <!doctype html>
                <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <title>Internal Server Error</title>
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

    private static boolean wantsJson(Request request) {
        return request.header("Accept")
                .map(value -> value.toLowerCase(java.util.Locale.ROOT).contains("application/json"))
                .orElse(false);
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
}
