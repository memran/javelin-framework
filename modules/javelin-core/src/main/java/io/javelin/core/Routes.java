package io.javelin.core;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public final class Routes {
    private Routes() {
    }

    public static Route.RouteBuilder get(String path, RouteHandler handler) {
        return Route.get(path, handler);
    }

    public static Route.RouteBuilder post(String path, RouteHandler handler) {
        return Route.post(path, handler);
    }

    public static Route.RouteBuilder put(String path, RouteHandler handler) {
        return Route.put(path, handler);
    }

    public static Route.RouteBuilder patch(String path, RouteHandler handler) {
        return Route.patch(path, handler);
    }

    public static Route.RouteBuilder delete(String path, RouteHandler handler) {
        return Route.delete(path, handler);
    }

    public static Router add(Router router, Route.RouteBuilder builder) {
        return Objects.requireNonNull(router, "router").add(Objects.requireNonNull(builder, "builder"));
    }

    public static Router middleware(Router router, Middleware middleware) {
        return Objects.requireNonNull(router, "router").middleware(Objects.requireNonNull(middleware, "middleware"));
    }

    public static void group(Router router, String routePrefix, Consumer<Router> routes) {
        group(router, routePrefix, List.of(), routes);
    }

    public static void group(Router router, String routePrefix, List<Middleware> middleware, Consumer<Router> routes) {
        Objects.requireNonNull(router, "router").group(routePrefix, List.copyOf(Objects.requireNonNull(middleware, "middleware")), Objects.requireNonNull(routes, "routes"));
    }
}
