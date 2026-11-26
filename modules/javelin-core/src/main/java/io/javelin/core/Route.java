package io.javelin.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public final class Route {
    private final HttpMethod method;
    private final String path;
    private final RouteHandler handler;
    private final List<Middleware> middleware;
    private final String name;
    private final Pattern pattern;
    private final List<String> parameterNames;

    Route(HttpMethod method, String path, RouteHandler handler, List<Middleware> middleware, String name) {
        this.method = method;
        this.path = normalize(path);
        this.handler = handler;
        this.middleware = List.copyOf(middleware);
        this.name = name;
        CompiledRoute compiled = compile(this.path);
        this.pattern = compiled.pattern();
        this.parameterNames = compiled.parameterNames();
    }

    public static RouteBuilder get(String path, RouteHandler handler) {
        return new RouteBuilder(HttpMethod.GET, path, handler);
    }

    public static RouteBuilder post(String path, RouteHandler handler) {
        return new RouteBuilder(HttpMethod.POST, path, handler);
    }

    public static RouteBuilder put(String path, RouteHandler handler) {
        return new RouteBuilder(HttpMethod.PUT, path, handler);
    }

    public static RouteBuilder patch(String path, RouteHandler handler) {
        return new RouteBuilder(HttpMethod.PATCH, path, handler);
    }

    public static RouteBuilder delete(String path, RouteHandler handler) {
        return new RouteBuilder(HttpMethod.DELETE, path, handler);
    }

    public HttpMethod method() {
        return method;
    }

    public String path() {
        return path;
    }

    public RouteHandler handler() {
        return handler;
    }

    public List<Middleware> middleware() {
        return middleware;
    }

    public Optional<String> name() {
        return Optional.ofNullable(name);
    }

    boolean hasParameters() {
        return !parameterNames.isEmpty();
    }

    /** Two routes with different parameter names can still compile to the same matcher. */
    boolean hasSamePattern(Route other) {
        return pattern.pattern().equals(other.pattern.pattern());
    }

    public List<String> parameterNames() {
        return parameterNames;
    }

    /** Builds a concrete URL path, filling and encoding each named parameter. */
    public String buildPath(Map<String, String> params) {
        if (parameterNames.isEmpty()) {
            return path;
        }
        StringBuilder result = new StringBuilder();
        for (String segment : path.split("/")) {
            if (segment.isEmpty()) {
                continue;
            }
            result.append('/');
            if (segment.startsWith("{") && segment.endsWith("}")) {
                String definition = segment.substring(1, segment.length() - 1);
                int colon = definition.indexOf(':');
                String name = colon < 0 ? definition : definition.substring(0, colon);
                String value = params.get(name);
                if (value == null || value.isBlank()) {
                    throw new IllegalArgumentException("Missing route parameter: " + name);
                }
                result.append(URLEncoder.encode(value, StandardCharsets.UTF_8).replace("+", "%20"));
            } else {
                result.append(segment);
            }
        }
        return result.length() == 0 ? "/" : result.toString();
    }

    Optional<Map<String, String>> matches(HttpMethod requestMethod, String requestPath) {
        if (method != requestMethod) {
            return Optional.empty();
        }
        Matcher matcher = pattern.matcher(normalize(requestPath));
        if (!matcher.matches()) {
            return Optional.empty();
        }
        Map<String, String> params = new java.util.LinkedHashMap<>();
        for (int i = 0; i < parameterNames.size(); i++) {
            params.put(parameterNames.get(i), matcher.group(i + 1));
        }
        return Optional.of(params);
    }

    static String normalize(String path) {
        if (path == null || path.isBlank() || "/".equals(path)) {
            return "/";
        }
        String normalized = path.startsWith("/") ? path : "/" + path;
        return normalized.endsWith("/") ? normalized.substring(0, normalized.length() - 1) : normalized;
    }

    private static CompiledRoute compile(String routePath) {
        List<String> names = new ArrayList<>();
        StringBuilder regex = new StringBuilder("^");
        for (String segment : routePath.split("/")) {
            if (segment.isEmpty()) {
                continue;
            }
            regex.append("/");
            if (segment.startsWith("{") && segment.endsWith("}")) {
                String definition = segment.substring(1, segment.length() - 1);
                int colon = definition.indexOf(':');
                String name = colon < 0 ? definition : definition.substring(0, colon);
                if (name.isBlank()) {
                    throw new IllegalArgumentException("Route parameter name must not be blank: " + routePath);
                }
                String constraint = colon < 0 ? "[^/]+" : definition.substring(colon + 1);
                if (constraint.isBlank()) {
                    throw new IllegalArgumentException("Route parameter constraint must not be blank: " + routePath);
                }
                try {
                    Pattern.compile(constraint);
                } catch (PatternSyntaxException exception) {
                    throw new IllegalArgumentException("Invalid route parameter constraint: " + constraint, exception);
                }
                names.add(name);
                // Exactly one capturing group per parameter; the inner group keeps
                // alternation and any groups in the constraint from shifting indices.
                regex.append("((?:").append(constraint).append("))");
            } else {
                regex.append(Pattern.quote(segment));
            }
        }
        if ("/".equals(routePath)) {
            regex.append("/");
        }
        regex.append("$");
        return new CompiledRoute(Pattern.compile(regex.toString()), List.copyOf(names));
    }

    private record CompiledRoute(Pattern pattern, List<String> parameterNames) {
    }

    public static final class RouteBuilder {
        private final HttpMethod method;
        private final String path;
        private final RouteHandler handler;
        private final List<Middleware> middleware = new ArrayList<>();
        private String name;

        private RouteBuilder(HttpMethod method, String path, RouteHandler handler) {
            this.method = method;
            this.path = path;
            this.handler = handler;
        }

        public RouteBuilder middleware(Middleware middleware) {
            this.middleware.add(middleware);
            return this;
        }

        public RouteBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Route build() {
            return new Route(method, path, handler, middleware, name);
        }
    }
}
