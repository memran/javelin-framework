package io.javelin.core;

import java.util.Map;

public final class RedirectResponse extends Response {
    public RedirectResponse(String location) {
        this(location, 302);
    }

    public RedirectResponse(String location, int status) {
        super(status, Map.of("Location", location), new byte[0]);
    }
}
