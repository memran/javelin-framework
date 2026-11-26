package io.javelin.http.common;

import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertTrue(index.bodyStream().isPresent());
        assertEquals("<h1>Hello</h1>", new String(bodyOf(index), StandardCharsets.UTF_8));
        assertEquals("text/html; charset=utf-8", index.headers().get("Content-Type"));
        assertEquals("text/css; charset=utf-8", stylesheet.headers().get("Content-Type"));
        assertEquals("body { color: #111; }", new String(bodyOf(stylesheet), StandardCharsets.UTF_8));
        assertEquals(0, head.body().length);
        assertEquals(21, head.contentLength());
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
        Response weak = resolver.resolve(request("/app.css", "If-None-Match", "W/" + etag)).orElseThrow();
        Response weakInList = resolver.resolve(request("/app.css", "If-None-Match", "\"stale\", W/" + etag))
                .orElseThrow();
        Response wildcard = resolver.resolve(request("/app.css", "If-None-Match", "*")).orElseThrow();
        Response changed = resolver.resolve(request("/app.css", "If-None-Match", "\"stale\"")).orElseThrow();

        assertEquals(304, notModified.status());
        assertEquals(0, notModified.body().length);
        assertEquals(etag, notModified.headers().get("ETag"));
        assertEquals(304, weak.status());
        assertEquals(304, weakInList.status());
        assertEquals(304, wildcard.status());
        assertEquals(200, changed.status());
    }

    @Test
    void reusesEtagsUntilAFileChanges() throws Exception {
        Path root = Files.createTempDirectory("javelin-static-etag-cache");
        Path file = root.resolve("app.css");
        Files.writeString(file, "body { color: #111; }");
        StaticAssetResolver resolver = new StaticAssetResolver(root);

        String first = resolver.resolve("GET", "/app.css").orElseThrow().headers().get("ETag");
        String cached = resolver.resolve("GET", "/app.css").orElseThrow().headers().get("ETag");
        assertEquals(first, cached);

        Files.writeString(file, "body { color: #222; }");
        Files.setLastModifiedTime(file, FileTime.from(Instant.now().plusSeconds(1)));
        String changed = resolver.resolve("GET", "/app.css").orElseThrow().headers().get("ETag");
        assertNotEquals(first, changed);
    }

    @Test
    void reportsEtagCacheHitRate() throws Exception {
        Path root = Files.createTempDirectory("javelin-static-etag-stats");
        Files.writeString(root.resolve("app.css"), "body { color: #111; }");
        StaticAssetResolver resolver = new StaticAssetResolver(root);

        resolver.resolve("GET", "/app.css");
        resolver.resolve("GET", "/app.css");
        StaticAssetResolver.EtagStats stats = resolver.etagStats();

        assertEquals(1, stats.hits());
        assertEquals(1, stats.misses());
        assertEquals(0.5, stats.hitRate());
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
        assertTrue(first.bodyStream().isPresent());
        assertEquals(6, first.contentLength());
        assertEquals("bytes 0-5/" + css.length(), first.headers().get("Content-Range"));
        assertEquals("body {", new String(bodyOf(first), StandardCharsets.UTF_8));
        assertEquals(206, suffix.status());
        assertEquals("bytes " + (css.length() - 4) + "-" + (css.length() - 1) + "/" + css.length(),
                suffix.headers().get("Content-Range"));
        assertEquals("1; }", new String(bodyOf(suffix), StandardCharsets.UTF_8));
        assertEquals(206, openEnded.status());
        assertEquals("color: #111; }", new String(bodyOf(openEnded), StandardCharsets.UTF_8));
        assertEquals(416, invalid.status());
        assertEquals("bytes */" + css.length(), invalid.headers().get("Content-Range"));
        assertEquals(200, malformed.status());
    }

    @Test
    void servesRangesAndConditionalRequestsForLargeFiles() throws Exception {
        Path root = Files.createTempDirectory("javelin-static-large");
        byte[] content = new byte[2 * 1024 * 1024];
        for (int index = 0; index < content.length; index++) {
            content[index] = (byte) (index % 251);
        }
        Files.write(root.resolve("large.bin"), content);
        StaticAssetResolver resolver = new StaticAssetResolver(root);
        String etag = resolver.resolve("HEAD", "/large.bin").orElseThrow().headers().get("ETag");

        Response head = resolver.resolve("HEAD", "/large.bin").orElseThrow();
        Response conditional = resolver.resolve(request("/large.bin", "If-None-Match", "W/" + etag)).orElseThrow();
        Response range = resolver.resolve(request("/large.bin", "Range", "bytes=100-199")).orElseThrow();
        Response suffix = resolver.resolve(request("/large.bin", "Range", "bytes=-4")).orElseThrow();

        assertEquals(0, head.body().length);
        assertEquals(etag, head.headers().get("ETag"));
        assertEquals(304, conditional.status());
        assertEquals(0, conditional.body().length);
        assertEquals(206, range.status());
        assertTrue(range.bodyStream().isPresent());
        assertEquals("bytes 100-199/" + content.length, range.headers().get("Content-Range"));
        assertArrayEquals(Arrays.copyOfRange(content, 100, 200), bodyOf(range));
        assertEquals(206, suffix.status());
        assertArrayEquals(Arrays.copyOfRange(content, content.length - 4, content.length), bodyOf(suffix));
    }

    @Test
    void supportsCustomImmutableCachePolicies() throws Exception {
        Path root = Files.createTempDirectory("javelin-static-immutable");
        Files.writeString(root.resolve("app.css"), "body {}", StandardCharsets.UTF_8);

        Response response = new StaticAssetResolver(root, new StaticCachePolicy(31_536_000, true))
                .resolve("GET", "/app.css").orElseThrow();

        assertEquals("public, max-age=31536000, immutable", response.headers().get("Cache-Control"));
    }

    private static byte[] bodyOf(Response response) throws IOException {
        if (response.bodyStream().isEmpty()) {
            return response.body();
        }
        try (InputStream input = response.bodyStream().orElseThrow()) {
            return input.readAllBytes();
        }
    }

    private static Request request(String path, String header, String value) {
        return new Request(HttpMethod.GET, path, Map.of(header, List.of(value)), Map.of(), Map.of(),
                new byte[0], "127.0.0.1");
    }
}
