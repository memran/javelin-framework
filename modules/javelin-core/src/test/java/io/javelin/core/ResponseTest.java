package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ResponseTest {
    @Test
    void responseFactoriesCoverCommonContentTypes() {
        Response json = Response.json(Map.of("status", "ok"));
        Response jsonCreated = Response.json(Map.of("status", "created"), 201);
        Response html = Response.html("<h1>Hello</h1>");
        Response htmlCreated = Response.html("<h1>Created</h1>", 201);
        Response text = Response.text("plain");
        Response textAccepted = Response.text("accepted", 202);
        Response xml = Response.xml("<root><status>ok</status></root>");
        Response xmlAccepted = Response.xml("<root><status>accepted</status></root>", 202);
        Response redirect = Response.redirect("/dashboard");
        Response moved = Response.redirect("/legacy", 301);
        Response download = Response.download("file-content".getBytes(StandardCharsets.UTF_8), "report.txt");
        Response csvDownload = Response.download("a,b,c".getBytes(StandardCharsets.UTF_8), "export.csv", "text/csv", 201);
        Response streamed = Response.stream(new ByteArrayInputStream("streamed".getBytes(StandardCharsets.UTF_8)), "text/plain");
        Response noContent = Response.noContent();
        Response acceptedNoContent = Response.noContent(202);
        Response error = Response.errorPage(500, "Internal Server Error", "Boom");

        assertEquals(200, json.status());
        assertEquals("application/json; charset=utf-8", json.headers().get("Content-Type"));
        assertEquals("{\"status\":\"ok\"}", new String(json.body(), StandardCharsets.UTF_8));
        assertEquals(201, jsonCreated.status());
        assertEquals("{\"status\":\"created\"}", new String(jsonCreated.body(), StandardCharsets.UTF_8));

        assertEquals(200, html.status());
        assertEquals("text/html; charset=utf-8", html.headers().get("Content-Type"));
        assertEquals("<h1>Hello</h1>", new String(html.body(), StandardCharsets.UTF_8));
        assertEquals(201, htmlCreated.status());

        assertEquals(200, text.status());
        assertEquals("text/plain; charset=utf-8", text.headers().get("Content-Type"));
        assertEquals("plain", new String(text.body(), StandardCharsets.UTF_8));
        assertEquals(202, textAccepted.status());
        assertEquals("accepted", new String(textAccepted.body(), StandardCharsets.UTF_8));

        assertEquals(200, xml.status());
        assertEquals("application/xml; charset=utf-8", xml.headers().get("Content-Type"));
        assertEquals("<root><status>ok</status></root>", new String(xml.body(), StandardCharsets.UTF_8));
        assertEquals(202, xmlAccepted.status());
        assertEquals("<root><status>accepted</status></root>", new String(xmlAccepted.body(), StandardCharsets.UTF_8));

        assertEquals(302, redirect.status());
        assertEquals("/dashboard", redirect.headers().get("Location"));
        assertEquals(301, moved.status());
        assertEquals("/legacy", moved.headers().get("Location"));

        assertEquals(200, download.status());
        assertEquals("application/octet-stream", download.headers().get("Content-Type"));
        assertEquals("attachment; filename=\"report.txt\"", download.headers().get("Content-Disposition"));
        assertEquals("file-content", new String(download.body(), StandardCharsets.UTF_8));

        assertEquals(201, csvDownload.status());
        assertEquals("text/csv", csvDownload.headers().get("Content-Type"));
        assertEquals("attachment; filename=\"export.csv\"", csvDownload.headers().get("Content-Disposition"));
        assertEquals("a,b,c", new String(csvDownload.body(), StandardCharsets.UTF_8));

        assertEquals(200, streamed.status());
        assertEquals("text/plain", streamed.headers().get("Content-Type"));
        assertEquals("streamed", new String(streamed.body(), StandardCharsets.UTF_8));

        assertEquals(204, noContent.status());
        assertTrue(noContent.headers().isEmpty());
        assertEquals(202, acceptedNoContent.status());

        assertEquals(500, error.status());
        assertEquals("text/html; charset=utf-8", error.headers().get("Content-Type"));
        String errorBody = new String(error.body(), StandardCharsets.UTF_8);
        assertTrue(errorBody.contains("Internal Server Error"));
        assertTrue(errorBody.contains("Boom"));
    }
}
