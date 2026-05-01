package io.javelin.http.jdk;

import io.javelin.core.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

final class StaticAssetResolver {
    private final Path rootDirectory;

    StaticAssetResolver(Path rootDirectory) {
        this.rootDirectory = rootDirectory.toAbsolutePath().normalize();
    }

    Optional<Response> resolve(String method, String requestPath) {
        if (method == null || requestPath == null) {
            return Optional.empty();
        }
        String upperMethod = method.toUpperCase();
        if (!upperMethod.equals("GET") && !upperMethod.equals("HEAD")) {
            return Optional.empty();
        }

        Path candidate = resolvePath(requestPath);
        if (candidate == null || !Files.exists(candidate)) {
            return Optional.empty();
        }
        if (Files.isDirectory(candidate)) {
            candidate = candidate.resolve("index.html");
            if (!Files.exists(candidate)) {
                return Optional.empty();
            }
        }

        try {
            byte[] content = Files.readAllBytes(candidate);
            String contentType = Files.probeContentType(candidate);
            if (contentType == null || contentType.isBlank()) {
                contentType = fallbackContentType(candidate);
            } else if (contentType.startsWith("text/") && !contentType.toLowerCase().contains("charset=")) {
                contentType = contentType + "; charset=utf-8";
            }
            byte[] body = upperMethod.equals("HEAD") ? new byte[0] : content;
            return Optional.of(new Response(200, Map.of("Content-Type", contentType), body));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read static asset " + candidate, exception);
        }
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
        String name = candidate.getFileName() == null ? "" : candidate.getFileName().toString().toLowerCase();
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
}
