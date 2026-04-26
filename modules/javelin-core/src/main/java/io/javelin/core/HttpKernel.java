package io.javelin.core;

import java.util.ArrayList;
import java.util.List;

public final class HttpKernel {
    private final Router router;
    private final ExceptionHandler exceptionHandler;

    public HttpKernel(Router router, ExceptionHandler exceptionHandler) {
        this.router = router;
        this.exceptionHandler = exceptionHandler;
    }

    public Response handle(Request request) {
        try {
            Router.ResolvedRoute resolved = router.resolve(request.method(), request.path())
                    .orElse(null);
            if (resolved == null) {
                return JsonResponse.error("Not Found", 404);
            }
            Request routedRequest = request.withParams(resolved.params());
            List<Middleware> pipeline = new ArrayList<>(router.globalMiddleware());
            pipeline.addAll(resolved.route().middleware());
            return dispatch(pipeline, 0, routedRequest, resolved.route().handler());
        } catch (Throwable throwable) {
            return exceptionHandler.handle(throwable, request);
        }
    }

    private Response dispatch(List<Middleware> middleware, int index, Request request, RouteHandler handler) throws Exception {
        if (index == middleware.size()) {
            return handler.handle(request);
        }
        return middleware.get(index).handle(request, next -> dispatch(middleware, index + 1, next, handler));
    }
}
