package io.javelin.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public final class Router {
    private final List<Route> routes = new ArrayList<>();
    private final Map<HttpMethod, Map<String, Route>> staticRoutes = new EnumMap<>(HttpMethod.class);
    private final Map<HttpMethod, List<Route>> patternRoutes = new EnumMap<>(HttpMethod.class);
    private final List<Middleware> globalMiddleware = new ArrayList<>();
    private String prefix = "";
    private final List<Middleware> groupMiddleware = new ArrayList<>();

    public Router get(String path, RouteHandler handler) {
        return add(Route.get(path, handler));
    }

    public Router post(String path, RouteHandler handler) {
        return add(Route.post(path, handler));
    }

    public Router put(String path, RouteHandler handler) {
        return add(Route.put(path, handler));
    }

    public Router patch(String path, RouteHandler handler) {
        return add(Route.patch(path, handler));
    }

    public Router delete(String path, RouteHandler handler) {
        return add(Route.delete(path, handler));
    }

    public Router add(Route.RouteBuilder builder) {
        Route base = builder.build();
        List<Middleware> middleware = new ArrayList<>(groupMiddleware);
        middleware.addAll(base.middleware());
        Route route = new Route(base.method(), prefix + base.path(), base.handler(), middleware, base.name().orElse(null));
        if (hasConflict(route)) {
            throw new IllegalArgumentException("Duplicate route registration: " + route.method() + " " + route.path());
        }
        if (route.name().filter(this::hasNameConflict).isPresent()) {
            throw new IllegalArgumentException("Duplicate route name: " + route.name().orElseThrow());
        }
        routes.add(route);
        if (route.hasParameters()) {
            patternRoutes.computeIfAbsent(route.method(), ignored -> new ArrayList<>()).add(route);
        } else {
            staticRoutes.computeIfAbsent(route.method(), ignored -> new HashMap<>()).put(route.path(), route);
        }
        return this;
    }

    public Router middleware(Middleware middleware) {
        globalMiddleware.add(middleware);
        return this;
    }

    public void group(String routePrefix, List<Middleware> middleware, Consumer<Router> routes) {
        String oldPrefix = prefix;
        List<Middleware> oldMiddleware = new ArrayList<>(groupMiddleware);
        prefix = oldPrefix + routePrefix;
        groupMiddleware.addAll(middleware);
        try {
            routes.accept(this);
        } finally {
            prefix = oldPrefix;
            groupMiddleware.clear();
            groupMiddleware.addAll(oldMiddleware);
        }
    }

    public List<Route> routes() {
        return List.copyOf(routes);
    }

    /** Finds a route by its name. */
    public Optional<Route> named(String name) {
        return routes.stream()
                .filter(route -> route.name().filter(name::equals).isPresent())
                .findFirst();
    }

    /** Generates a URL path for a named route, filling its parameters. */
    public String path(String name, Map<String, String> params) {
        return named(name)
                .orElseThrow(() -> new IllegalArgumentException("Unknown route name: " + name))
                .buildPath(params);
    }

    /**
     * Methods that would match this path, used to build {@code Allow} on a 405 response.
     * HEAD is included whenever GET is registered because GET routes accept HEAD.
     */
    public List<HttpMethod> allowedMethods(String path) {
        if (path == null) {
            return List.of();
        }
        EnumSet<HttpMethod> allowed = EnumSet.noneOf(HttpMethod.class);
        for (HttpMethod method : HttpMethod.values()) {
            if (resolveExact(method, path).isPresent()) {
                allowed.add(method);
            }
        }
        if (allowed.contains(HttpMethod.GET)) {
            allowed.add(HttpMethod.HEAD);
        }
        List<HttpMethod> ordered = new ArrayList<>(allowed);
        ordered.sort(Comparator.comparingInt(Enum::ordinal));
        return List.copyOf(ordered);
    }

    private boolean hasNameConflict(String name) {
        return routes.stream().anyMatch(route -> route.name().filter(name::equals).isPresent());
    }

    private boolean hasConflict(Route route) {
        if (route.hasParameters()) {
            return patternRoutes.getOrDefault(route.method(), List.of()).stream()
                    .anyMatch(existing -> existing.hasSamePattern(route));
        }
        return staticRoutes.getOrDefault(route.method(), Map.of()).containsKey(route.path());
    }

    Optional<ResolvedRoute> resolve(HttpMethod method, String path) {
        Optional<ResolvedRoute> resolved = resolveExact(method, path);
        if (resolved.isPresent() || method != HttpMethod.HEAD) {
            return resolved;
        }
        return resolveExact(HttpMethod.GET, path);
    }

    private Optional<ResolvedRoute> resolveExact(HttpMethod method, String path) {
        Route staticRoute = staticRoutes.getOrDefault(method, Map.of()).get(Route.normalize(path));
        if (staticRoute != null) {
            return Optional.of(new ResolvedRoute(staticRoute, Map.of()));
        }
        for (Route route : patternRoutes.getOrDefault(method, List.of())) {
            Optional<Map<String, String>> params = route.matches(method, path);
            if (params.isPresent()) {
                return Optional.of(new ResolvedRoute(route, params.get()));
            }
        }
        return Optional.empty();
    }

    List<Middleware> globalMiddleware() {
        return List.copyOf(globalMiddleware);
    }

    record ResolvedRoute(Route route, java.util.Map<String, String> params) {
    }
}
