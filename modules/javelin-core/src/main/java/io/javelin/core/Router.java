package io.javelin.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public final class Router {
    private final List<Route> routes = new ArrayList<>();
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
        routes.add(new Route(base.method(), prefix + base.path(), base.handler(), middleware, base.name().orElse(null)));
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

    Optional<ResolvedRoute> resolve(HttpMethod method, String path) {
        for (Route route : routes) {
            Optional<java.util.Map<String, String>> params = route.matches(method, path);
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
