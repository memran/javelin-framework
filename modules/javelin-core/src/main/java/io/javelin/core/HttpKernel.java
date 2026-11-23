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

    public HttpKernel(Router router, ExceptionHandler exceptionHandler) {
        this(router, exceptionHandler, request -> Optional.empty());
    }

    private HttpKernel(Router router, ExceptionHandler exceptionHandler, Function<Request, Optional<Response>> staticFallback) {
        this.router = router;
        this.exceptionHandler = exceptionHandler;
        this.staticFallback = staticFallback;
    }

    public HttpKernel withStaticFallback(Function<Request, Optional<Response>> fallback) {
        return new HttpKernel(router, exceptionHandler, fallback);
    }

    public Response handle(Request request) {
        try {
            Optional<Response> staticResponse = staticFallback.apply(request);
            if (staticResponse.isPresent()) {
                Response response = staticResponse.get();
                return dispatch(new ArrayList<>(router.globalMiddleware()), 0, request, routed -> response);
            }
            Router.ResolvedRoute resolved = router.resolve(request.method(), request.path())
                    .orElse(null);
            if (resolved == null) {
                List<HttpMethod> allowed = router.allowedMethods(request.path());
                if (!allowed.isEmpty()) {
                    String allow = allowed.stream().map(HttpMethod::name).collect(Collectors.joining(", "));
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
