package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RequestMultipartTest {
    @Test
    void parsesMultipartFormFieldsAndUploadedFiles() throws Exception {
        String boundary = "JavelinBoundary";
        byte[] body = ("""
                --JavelinBoundary\r
                Content-Disposition: form-data; name="title"\r
                \r
                Hello World\r
                --JavelinBoundary\r
                Content-Disposition: form-data; name="avatar"; filename="../../CON.txt"\r
                Content-Type: text/plain\r
                \r
                avatar-bytes\r
                --JavelinBoundary--\r
                """).getBytes(StandardCharsets.UTF_8);

        Request request = new Request(
                HttpMethod.POST,
                "/profiles",
                Map.of("Content-Type", List.of("multipart/form-data; boundary=" + boundary)),
                Map.of("page", "2"),
                Map.of(),
                body,
                "127.0.0.1"
        );

        assertEquals("Hello World", request.input().text("title").orElseThrow());
        assertEquals("2", request.input().raw("page").orElseThrow());
        assertTrue(request.files("avatar").size() == 1);
        assertEquals(1, request.uploads().size());

        UploadedFile upload = request.file("avatar").orElseThrow();
        assertEquals(upload, request.upload("avatar").orElseThrow());
        assertEquals("avatar", upload.fieldName());
        assertEquals("../../CON.txt", upload.clientFilename());
        assertEquals("_CON.txt", upload.safeFilename());
        assertEquals("text/plain", upload.contentType());
        assertArrayEquals("avatar-bytes".getBytes(StandardCharsets.UTF_8), upload.body());

        Path directory = Files.createTempDirectory("javelin-upload");
        assertEquals(directory.resolve("_CON.txt"), request.saveFile("avatar", directory).orElseThrow());
        Path saved = upload.saveToDirectory(directory);
        assertEquals(directory.resolve("_CON.txt"), saved);
        assertArrayEquals("avatar-bytes".getBytes(StandardCharsets.UTF_8), Files.readAllBytes(saved));
    }
}
