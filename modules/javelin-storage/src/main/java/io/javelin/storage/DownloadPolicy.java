package io.javelin.storage;

import io.javelin.core.Request;

/** Authorization hook for private disk downloads. */
@FunctionalInterface
public interface DownloadPolicy {
    boolean allow(String disk, String path, Request request);
}
