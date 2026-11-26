package io.javelin.observability;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.testing.exporter.InMemorySpanExporter;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Verifies that the optional OTel adapter produces real spans when an SDK is installed. */
final class OpenTelemetryTracerTest {
    @Test
    void exportsSpansAndRecordsFailuresThroughTheGlobalTracer() {
        GlobalOpenTelemetry.resetForTest();
        InMemorySpanExporter exporter = InMemorySpanExporter.create();
        SdkTracerProvider provider = SdkTracerProvider.builder()
                .addSpanProcessor(SimpleSpanProcessor.create(exporter))
                .build();
        OpenTelemetrySdk sdk = OpenTelemetrySdk.builder().setTracerProvider(provider).build();
        GlobalOpenTelemetry.set(sdk);
        try {
            Tracer tracer = new OpenTelemetryTracer();
            assertEquals("ok", tracer.span("demo.span", () -> "ok"));
            assertThrows(IllegalStateException.class,
                    () -> tracer.span("failing.span", () -> {
                        throw new IllegalStateException("boom");
                    }));

            List<SpanData> spans = exporter.getFinishedSpanItems();
            assertEquals(2, spans.size());
            assertEquals("demo.span", spans.get(0).getName());
            assertEquals(StatusCode.UNSET, spans.get(0).getStatus().getStatusCode());
            assertEquals("failing.span", spans.get(1).getName());
            assertEquals(StatusCode.ERROR, spans.get(1).getStatus().getStatusCode());
            assertTrue(spans.get(1).getEvents().stream()
                    .anyMatch(event -> event.getName().equals("exception")));
        } finally {
            GlobalOpenTelemetry.resetForTest();
            sdk.close();
        }
    }
}
