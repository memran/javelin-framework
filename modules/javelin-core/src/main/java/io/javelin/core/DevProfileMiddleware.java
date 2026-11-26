package io.javelin.core;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Added by the starter only in the dev profile. Marks responses as development
 * output and prevents caching so template and asset changes are visible immediately.
 */
public final class DevProfileMiddleware implements Middleware {
    @Override
    public Response handle(Request request, Next next) throws Exception {
        Response response = next.handle(request);
        Map<String, String> headers = new LinkedHashMap<>(response.headers());
        headers.put("X-Javelin-Profile", "dev");
        headers.put("Cache-Control", "no-store");
        return response.withHeaders(headers);
    }
}
