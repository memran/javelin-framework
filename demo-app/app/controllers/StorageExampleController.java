package app.controllers;

import io.javelin.core.Json;
import io.javelin.core.JsonResponse;
import io.javelin.core.Request;
import io.javelin.core.Response;
import io.javelin.security.CsrfMiddleware;
import io.javelin.storage.StoredFile;
import io.javelin.storages.Storage;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/** Runnable examples for docs/tutorials/files-and-cache.md. */
public final class StorageExampleController {
    public Response form(Request request) {
        String token = request.attribute(CsrfMiddleware.TOKEN_KEY).map(Object::toString).orElse("");
        String html = """
                <!doctype html><html lang="en"><head><meta charset="utf-8"><title>Upload</title></head><body>
                <h1>Upload a file</h1>
                <form method="post" enctype="multipart/form-data" action="/demo/files">
                  <input type="hidden" name="_token" value="%s">
                  <input type="file" name="file">
                  <button type="submit">Upload</button>
                </form>
                </body></html>
                """.formatted(token.replace("\"", "&quot;"));
        return Response.html(html);
    }

    public Response upload(Request request) {
        var upload = request.file("file").orElse(null);
        if (upload == null) {
            return JsonResponse.error("Missing file", 422);
        }
        StoredFile stored = Storage.disk("public").put(
                "uploads/" + upload.safeFilename(), upload.body(), upload.safeFilename(), upload.contentType());
        return Json.ok(Map.of(
                "url", stored.url(),
                "filename", stored.filename(),
                "size", stored.size()));
    }

    public Response download(Request request) {
        String path = request.query("path").orElse("");
        return Storage.download("private", path, request);
    }

    public JsonResponse cache(Request request) {
        io.javelin.caches.Cache.put("demo:visits", 1, java.time.Duration.ofMinutes(5));
        return Json.ok(Map.of("visits",
                io.javelin.caches.Cache.get("demo:visits").orElse(0)));
    }
}
