package io.javelin.storage;

import io.javelin.core.Request;
import io.javelin.core.security.Authenticated;
import io.javelin.core.security.BearerAuthMiddleware;

/** Allows private downloads only for an authenticated principal. */
public final class AuthenticatedDownloadPolicy implements DownloadPolicy {
    @Override
    public boolean allow(String disk, String path, Request request) {
        return request.attribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE)
                .filter(Authenticated.class::isInstance)
                .isPresent();
    }
}
