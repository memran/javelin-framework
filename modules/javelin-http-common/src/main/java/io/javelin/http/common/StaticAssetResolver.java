package io.javelin.http.common;

import io.javelin.core.Request;
import io.javelin.core.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/** Resolves static files under a fixed public root, with caching, conditional and range support. */
public final class StaticAssetResolver {
    private static final ByteRange UNSATISFIABLE = new ByteRange(-1, -1);

    private final Path rootDirectory;
    private final StaticCachePolicy cachePolicy;

    public StaticAssetResolver(Path rootDirectory) {
        this(rootDirectory, StaticCachePolicy.DEFAULT);
    }

    public StaticAssetResolver(Path rootDirectory, StaticCachePolicy cachePolicy) {
        this.rootDirectory = rootDirectory.toAbsolutePath().normalize();
        this.cachePolicy = Objects.requireNonNull(cachePolicy, "cachePolicy");
    }

    public Optional<Response> resolve(String method, String requestPath) {
        return resolve(method, requestPath, ignored -> Optional.empty());
    }

    /** Uses request headers for conditional and range requests. */
    public Optional<Response> resolve(Request request) {
        Objects.requireNonNull(request, "request");
        return resolve(request.method().name(), request.path(), request::header);
    }

    private Optional<Response> resolve(String method, String requestPath, Function<String, Optional<String>> header) {
        if (method == null || requestPath == null) {
            return Optional.empty();
        }
        String upperMethod = method.toUpperCase(Locale.ROOT);
        if (!upperMethod.equals("GET") && !upperMethod.equals("HEAD")) {
            return Optional.empty();
        }

        Path candidate = resolvePath(requestPath);
        if (candidate == null || !Files.exists(candidate)) {
            return Optional.empty();
        }
        try {
            Path real = candidate.toRealPath();
            if (!real.startsWith(rootDirectory.toRealPath()) || !Files.isRegularFile(real)) {
                return Optional.empty();
            }

            byte[] content = Files.readAllBytes(real);
            String etag = etagOf(content);
            Map<String, String> headers = new LinkedHashMap<>();
            headers.put("Content-Type", contentTypeOf(real));
            headers.put("Cache-Control", cachePolicy.headerValue());
            headers.put("ETag", etag);
            headers.put("Accept-Ranges", "bytes");
            headers.put("X-Content-Type-Options", "nosniff");

            if (header.apply("If-None-Match").filter(value -> matchesEtag(value, etag)).isPresent()) {
                return Optional.of(new Response(304, Map.of(
                        "Cache-Control", cachePolicy.headerValue(),
                        "ETag", etag
                ), new byte[0]));
            }

            if (upperMethod.equals("GET")) {
                Optional<ByteRange> range = header.apply("Range")
                        .flatMap(value -> parseRange(value, content.length));
                if (range.isPresent()) {
                    ByteRange selected = range.get();
                    if (selected.equals(UNSATISFIABLE)) {
                        return Optional.of(new Response(416, Map.of(
                                "Content-Range", "bytes */" + content.length,
                                "Accept-Ranges", "bytes",
                                "X-Content-Type-Options", "nosniff"
                        ), "Range Not Satisfiable".getBytes(StandardCharsets.UTF_8)));
                    }
                    headers.put("Content-Range",
                            "bytes " + selected.start() + "-" + selected.end() + "/" + content.length);
                    return Optional.of(new Response(206, headers,
                            Arrays.copyOfRange(content, selected.start(), selected.end() + 1)));
                }
            }

            byte[] body = upperMethod.equals("HEAD") ? new byte[0] : content;
            return Optional.of(new Response(200, headers, body));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read static asset " + candidate, exception);
        }
    }

    private static boolean matchesEtag(String headerValue, String etag) {
        for (String token : headerValue.split(",")) {
            String value = token.trim();
            if ("*".equals(value) || value.equals(etag)) {
                return true;
            }
        }
        return false;
    }

    private static Optional<ByteRange> parseRange(String value, long length) {
        if (value == null || !value.startsWith("bytes=") || value.contains(",")) {
            return Optional.empty();
        }
        String spec = value.substring("bytes=".length()).trim();
        int dash = spec.indexOf('-');
        if (dash < 0) {
            return Optional.empty();
        }
        try {
            if (dash == 0) {
                long suffix = Long.parseLong(spec.substring(1));
                if (suffix <= 0) {
                    return Optional.of(UNSATISFIABLE);
                }
                long start = Math.max(0, length - suffix);
                if (start >= length) {
                    return Optional.of(UNSATISFIABLE);
                }
                return Optional.of(new ByteRange((int) start, (int) length - 1));
            }
            long start = Long.parseLong(spec.substring(0, dash));
            String endText = spec.substring(dash + 1);
            long end = endText.isEmpty() ? length - 1 : Long.parseLong(endText);
            if (start < 0 || start >= length || end < start) {
                return Optional.of(UNSATISFIABLE);
            }
            return Optional.of(new ByteRange((int) start, (int) Math.min(end, length - 1)));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    private static String etagOf(byte[] content) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(content);
            return "\"" + HexFormat.of().formatHex(digest) + "\"";
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    private String contentTypeOf(Path path) throws IOException {
        String contentType = Files.probeContentType(path);
        if (contentType == null || contentType.isBlank()) {
            return fallbackContentType(path);
        }
        if (contentType.startsWith("text/") && !contentType.toLowerCase(Locale.ROOT).contains("charset=")) {
            return contentType + "; charset=utf-8";
        }
        return contentType;
    }

    private Path resolvePath(String requestPath) {
        String normalized = requestPath.startsWith("/") ? requestPath.substring(1) : requestPath;
        if (normalized.isBlank()) {
            return resolveRelative("index.html");
        }
        if (normalized.endsWith("/")) {
            normalized = normalized + "index.html";
        }
        Path candidate = resolveRelative(normalized);
        if (candidate == null) {
            return null;
        }
        if (Files.isDirectory(candidate)) {
            return candidate.resolve("index.html");
        }
        return candidate;
    }

    private Path resolveRelative(String relativePath) {
        Path candidate = rootDirectory;
        for (String part : relativePath.split("/")) {
            if (part.isBlank() || ".".equals(part)) {
                continue;
            }
            if ("..".equals(part)) {
                return null;
            }
            candidate = candidate.resolve(part);
        }
        candidate = candidate.normalize();
        return candidate.startsWith(rootDirectory) ? candidate : null;
    }

    private String fallbackContentType(Path candidate) {
        String name = candidate.getFileName() == null ? "" : candidate.getFileName().toString().toLowerCase(Locale.ROOT);
        if (name.endsWith(".html") || name.endsWith(".htm")) {
            return "text/html; charset=utf-8";
        }
        if (name.endsWith(".css")) {
            return "text/css; charset=utf-8";
        }
        if (name.endsWith(".js") || name.endsWith(".mjs")) {
            return "text/javascript; charset=utf-8";
        }
        if (name.endsWith(".json")) {
            return "application/json; charset=utf-8";
        }
        if (name.endsWith(".xml")) {
            return "application/xml; charset=utf-8";
        }
        if (name.endsWith(".svg")) {
            return "image/svg+xml";
        }
        if (name.endsWith(".png")) {
            return "image/png";
        }
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (name.endsWith(".gif")) {
            return "image/gif";
        }
        if (name.endsWith(".webp")) {
            return "image/webp";
        }
        if (name.endsWith(".ico")) {
            return "image/x-icon";
        }
        return "application/octet-stream";
    }

    private record ByteRange(int start, int end) {
    }
}
