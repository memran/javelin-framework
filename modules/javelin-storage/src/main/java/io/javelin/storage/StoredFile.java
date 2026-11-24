package io.javelin.storage;

/** Metadata returned by a successful disk write. */
public record StoredFile(String url, String path, String filename, long size, String contentType) {
}
