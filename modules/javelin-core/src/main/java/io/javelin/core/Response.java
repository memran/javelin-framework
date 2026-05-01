package io.javelin.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class Response {
    private final int status;
    private final Map<String, String> headers;
    private final byte[] body;

    public Response(int status, Map<String, String> headers, byte[] body) {
        this.status = status;
        this.headers = new LinkedHashMap<>(headers);
        this.body = body == null ? new byte[0] : body.clone();
    }

    public static Response text(String body) {
        return text(body, 200);
    }

    public static JsonResponse json(Object data) {
        return json(data, 200);
    }

    public static JsonResponse json(Object data, int status) {
        return JsonResponse.of(status, data);
    }

    public static HtmlResponse html(String html) {
        return html(html, 200);
    }

    public static HtmlResponse html(String html, int status) {
        return new HtmlResponse(html, status);
    }

    public static Response xml(String xml) {
        return xml(xml, 200);
    }

    public static Response text(String body, int status) {
        return new Response(status, Map.of("Content-Type", "text/plain; charset=utf-8"), body.getBytes(StandardCharsets.UTF_8));
    }

    public static Response xml(String xml, int status) {
        return new Response(status, Map.of("Content-Type", "application/xml; charset=utf-8"), xml.getBytes(StandardCharsets.UTF_8));
    }

    public static RedirectResponse redirect(String location) {
        return new RedirectResponse(location);
    }

    public static RedirectResponse redirect(String location, int status) {
        return new RedirectResponse(location, status);
    }

    public static Response download(byte[] content, String filename) {
        return download(content, filename, "application/octet-stream", 200);
    }

    public static Response download(byte[] content, String filename, String contentType) {
        return download(content, filename, contentType, 200);
    }

    public static Response download(byte[] content, String filename, String contentType, int status) {
        return new Response(status, Map.of(
                "Content-Type", contentType,
                "Content-Disposition", "attachment; filename=\"" + escapeHeaderValue(filename) + "\""
        ), content);
    }

    public static Response stream(InputStream content, String contentType) {
        return stream(content, contentType, 200);
    }

    public static Response stream(InputStream content, String contentType, int status) {
        return new Response(status, Map.of("Content-Type", contentType), readAllBytes(content));
    }

    public static Response noContent() {
        return noContent(204);
    }

    public static Response noContent(int status) {
        return new Response(status, Map.of(), new byte[0]);
    }

    public static HtmlResponse errorPage(int status, String title, String message) {
        String safeTitle = escapeHtml(title);
        String safeMessage = escapeHtml(message);
        String body = """
                <!doctype html>
                <html lang="en">
                <head>
                    <meta charset="utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <title>%s</title>
                    <style>
                        :root { color-scheme: light; }
                        body { margin: 0; font-family: system-ui, sans-serif; background: #f6f7f9; color: #1f2937; }
                        main { max-width: 720px; margin: 8rem auto; padding: 2rem; background: white; border: 1px solid #e5e7eb; border-radius: 1rem; box-shadow: 0 10px 30px rgba(15, 23, 42, 0.08); }
                        h1 { margin: 0 0 0.75rem; font-size: 2rem; }
                        p { margin: 0.5rem 0; line-height: 1.6; }
                        .code { font-size: 0.875rem; text-transform: uppercase; letter-spacing: 0.08em; color: #6b7280; }
                    </style>
                </head>
                <body>
                    <main>
                        <p class="code">%d</p>
                        <h1>%s</h1>
                        <p>%s</p>
                    </main>
                </body>
                </html>
                """.formatted(safeTitle, status, safeTitle, safeMessage);
        return new HtmlResponse(body, status);
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

    private static String escapeHeaderValue(String value) {
        StringBuilder builder = new StringBuilder(value.length());
        for (char character : value.toCharArray()) {
            if (character == '\\' || character == '"') {
                builder.append('\\');
            }
            if (character >= 0x20 && character != 0x7f) {
                builder.append(character);
            }
        }
        return builder.toString();
    }

    private static byte[] readAllBytes(InputStream content) {
        try {
            return content.readAllBytes();
        } catch (IOException exception) {
            throw new IllegalArgumentException("Unable to read streaming response content", exception);
        }
    }

    public int status() {
        return status;
    }

    public Map<String, String> headers() {
        return Map.copyOf(headers);
    }

    public byte[] body() {
        return body.clone();
    }
}
