package io.javelin.security;

import io.javelin.core.JsonResponse;
import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

public final class RequestSizeLimitMiddleware implements Middleware {
    private final int maxBytes;

    public RequestSizeLimitMiddleware(int maxBytes) {
        this.maxBytes = maxBytes;
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        if (request.body().length > maxBytes) {
            return JsonResponse.error("Payload Too Large", 413);
        }
        return next.handle(request);
    }
}
