package io.javelin.mail;

import java.util.Arrays;
import java.util.Objects;

/** An attachment or inline image. Inline parts carry a content id for {@code cid:} references. */
public record Attachment(String filename, String contentType, byte[] content, String contentId) {
    public Attachment {
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Attachment filename must not be blank");
        }
        contentType = contentType == null || contentType.isBlank() ? "application/octet-stream" : contentType;
        content = Arrays.copyOf(Objects.requireNonNull(content, "content"), content.length);
    }

    public static Attachment file(String filename, String contentType, byte[] content) {
        return new Attachment(filename, contentType, content, null);
    }

    public static Attachment image(String contentId, String contentType, byte[] content) {
        return new Attachment(contentId, contentType, content, contentId);
    }

    public boolean inline() {
        return contentId != null && !contentId.isBlank();
    }

    @Override
    public byte[] content() {
        return content.clone();
    }
}
