package io.javelin.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class HttpKernel {
    private final Router router;
    private final ExceptionHandler exceptionHandler;
    private final Function<Request, Optional<Response>> staticFallback;
    private final boolean staticFirst;

    public HttpKernel(Router router, ExceptionHandler exceptionHandler) {
        this(router, exceptionHandler, request -> Optional.empty(), true);
    }

    private HttpKernel(Router router, ExceptionHandler exceptionHandler,
                       Function<Request, Optional<Response>> staticFallback, boolean staticFirst) {
        this.router = router;
        this.exceptionHandler = exceptionHandler;
        this.staticFallback = staticFallback;
        this.staticFirst = staticFirst;
    }

    public HttpKernel withStaticFallback(Function<Request, Optional<Response>> fallback) {
        return new HttpKernel(router, exceptionHandler, fallback, staticFirst);
    }

    /** Tries static assets only after routing, so a matching route wins over a same-path file. */
    public HttpKernel staticAfterRoutes() {
        return new HttpKernel(router, exceptionHandler, staticFallback, false);
    }

    public Response handle(Request request) {
        try {
            if (staticFirst) {
                Optional<Response> staticResponse = staticFallback.apply(request);
                if (staticResponse.isPresent()) {
                    return staticResponse(staticResponse.get(), request);
                }
            }
            Router.ResolvedRoute resolved = router.resolve(request.method(), request.path())
                    .orElse(null);
            if (resolved == null) {
                if (!staticFirst) {
                    Optional<Response> staticResponse = staticFallback.apply(request);
                    if (staticResponse.isPresent()) {
                        return staticResponse(staticResponse.get(), request);
                    }
                }
                List<HttpMethod> allowed = router.allowedMethods(request.path());
                if (!allowed.isEmpty()) {
                    String allow = allowed.stream().map(HttpMethod::name).collect(Collectors.joining(", "));
                    if (request.method() == HttpMethod.OPTIONS) {
                        Response preflight = Response.noContent().withHeader("Allow", allow);
                        return dispatch(new ArrayList<>(router.globalMiddleware()), 0, request,
                                ignored -> preflight);
                    }
                    Response response = wantsJson(request)
                            ? JsonResponse.error("Method Not Allowed", 405)
                            : Response.text("Method Not Allowed", 405);
                    return response.withHeader("Allow", allow);
                }
                return wantsJson(request)
                        ? JsonResponse.error("Not Found", 404)
                        : Response.errorPage(404, "Not Found", "The requested resource could not be found.");
            }
            Request routedRequest = request.withParams(resolved.params());
            List<Middleware> pipeline = new ArrayList<>(router.globalMiddleware());
            pipeline.addAll(resolved.route().middleware());
            return dispatch(pipeline, 0, routedRequest, resolved.route().handler());
        } catch (Exception exception) {
            return exceptionHandler.handle(exception, request);
        }
    }

    private Response staticResponse(Response response, Request request) throws Exception {
        return dispatch(new ArrayList<>(router.globalMiddleware()), 0, request, ignored -> response);
    }

    private Response dispatch(List<Middleware> middleware, int index, Request request, RouteHandler handler) throws Exception {
        if (index == middleware.size()) {
            return handler.handle(request);
        }
        return middleware.get(index).handle(request, next -> dispatch(middleware, index + 1, next, handler));
    }

    private static boolean wantsJson(Request request) {
        return request.header("Accept")
                .map(value -> value.toLowerCase().contains("application/json"))
                .orElse(false);
    }
}
