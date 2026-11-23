package io.javelin.http.common;

import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class StaticAssetResolverTest {
    @Test
    void servesFilesFromTheConfiguredStaticRoot() throws Exception {
        Path root = Files.createTempDirectory("javelin-static");
        Files.writeString(root.resolve("index.html"), "<h1>Hello</h1>", StandardCharsets.UTF_8);
        Files.createDirectories(root.resolve("css"));
        Files.writeString(root.resolve("css/app.css"), "body { color: #111; }", StandardCharsets.UTF_8);

        StaticAssetResolver resolver = new StaticAssetResolver(root);

        Response index = resolver.resolve("GET", "/").orElseThrow();
        Response stylesheet = resolver.resolve("GET", "/css/app.css").orElseThrow();
        Response head = resolver.resolve("HEAD", "/css/app.css").orElseThrow();

        assertEquals(200, index.status());
        assertEquals("<h1>Hello</h1>", new String(index.body(), StandardCharsets.UTF_8));
        assertEquals("text/html; charset=utf-8", index.headers().get("Content-Type"));
        assertEquals("text/css; charset=utf-8", stylesheet.headers().get("Content-Type"));
        assertEquals("body { color: #111; }", new String(stylesheet.body(), StandardCharsets.UTF_8));
        assertEquals(0, head.body().length);
        assertTrue(resolver.resolve("GET", "/../secret.txt").isEmpty());
        assertTrue(resolver.resolve("POST", "/css/app.css").isEmpty());
    }

    @Test
    void addsCacheValidationAndMimeSafetyHeaders() throws Exception {
        Path root = Files.createTempDirectory("javelin-static-headers");
        Files.writeString(root.resolve("app.css"), "body { color: #111; }", StandardCharsets.UTF_8);

        Response response = new StaticAssetResolver(root).resolve("GET", "/app.css").orElseThrow();

        assertEquals("public, max-age=3600", response.headers().get("Cache-Control"));
        assertTrue(response.headers().get("ETag").startsWith("\""));
        assertEquals("bytes", response.headers().get("Accept-Ranges"));
        assertEquals("nosniff", response.headers().get("X-Content-Type-Options"));
    }

    @Test
    void returns304ForMatchingConditionalRequests() throws Exception {
        Path root = Files.createTempDirectory("javelin-static-etag");
        Files.writeString(root.resolve("app.css"), "body { color: #111; }", StandardCharsets.UTF_8);
        StaticAssetResolver resolver = new StaticAssetResolver(root);
        String etag = resolver.resolve("GET", "/app.css").orElseThrow().headers().get("ETag");

        Response notModified = resolver.resolve(request("/app.css", "If-None-Match", etag)).orElseThrow();
        Response wildcard = resolver.resolve(request("/app.css", "If-None-Match", "*")).orElseThrow();
        Response changed = resolver.resolve(request("/app.css", "If-None-Match", "\"stale\"")).orElseThrow();

        assertEquals(304, notModified.status());
        assertEquals(0, notModified.body().length);
        assertEquals(etag, notModified.headers().get("ETag"));
        assertEquals(304, wildcard.status());
        assertEquals(200, changed.status());
    }

    @Test
    void servesSatisfiableByteRangesAndRejectsUnsatisfiableOnes() throws Exception {
        Path root = Files.createTempDirectory("javelin-static-range");
        String css = "body { color: #111; }";
        Files.writeString(root.resolve("app.css"), css, StandardCharsets.UTF_8);
        StaticAssetResolver resolver = new StaticAssetResolver(root);

        Response first = resolver.resolve(request("/app.css", "Range", "bytes=0-5")).orElseThrow();
        Response suffix = resolver.resolve(request("/app.css", "Range", "bytes=-4")).orElseThrow();
        Response openEnded = resolver.resolve(request("/app.css", "Range", "bytes=7-")).orElseThrow();
        Response invalid = resolver.resolve(request("/app.css", "Range", "bytes=999-")).orElseThrow();
        Response malformed = resolver.resolve(request("/app.css", "Range", "bytes=abc")).orElseThrow();

        assertEquals(206, first.status());
        assertEquals("bytes 0-5/" + css.length(), first.headers().get("Content-Range"));
        assertEquals("body {", new String(first.body(), StandardCharsets.UTF_8));
        assertEquals(206, suffix.status());
        assertEquals("bytes " + (css.length() - 4) + "-" + (css.length() - 1) + "/" + css.length(),
                suffix.headers().get("Content-Range"));
        assertEquals("1; }", new String(suffix.body(), StandardCharsets.UTF_8));
        assertEquals(206, openEnded.status());
        assertEquals("color: #111; }", new String(openEnded.body(), StandardCharsets.UTF_8));
        assertEquals(416, invalid.status());
        assertEquals("bytes */" + css.length(), invalid.headers().get("Content-Range"));
        assertEquals(200, malformed.status());
    }

    @Test
    void supportsCustomImmutableCachePolicies() throws Exception {
        Path root = Files.createTempDirectory("javelin-static-immutable");
        Files.writeString(root.resolve("app.css"), "body {}", StandardCharsets.UTF_8);

        Response response = new StaticAssetResolver(root, new StaticCachePolicy(31_536_000, true))
                .resolve("GET", "/app.css").orElseThrow();

        assertEquals("public, max-age=31536000, immutable", response.headers().get("Cache-Control"));
    }

    private static Request request(String path, String header, String value) {
        return new Request(HttpMethod.GET, path, Map.of(header, List.of(value)), Map.of(), Map.of(),
                new byte[0], "127.0.0.1");
    }
}
