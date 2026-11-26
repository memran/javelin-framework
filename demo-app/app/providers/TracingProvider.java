package app.providers;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;

/**
 * Example OpenTelemetry wiring: installs an OTLP/HTTP exporter only when
 * OTEL_EXPORTER_OTLP_ENDPOINT is set, so local runs stay dependency-free.
 */
public final class TracingProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        String endpoint = app.env().get("OTEL_EXPORTER_OTLP_ENDPOINT").orElse("");
        if (endpoint.isBlank()) {
            return;
        }
        OtlpGrpcSpanExporter exporter = OtlpGrpcSpanExporter.builder()
                .setEndpoint(endpoint)
                .build();
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(exporter).build())
                .build();
        // buildAndRegisterGlobal() installs GlobalOpenTelemetry exactly once.
        OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .buildAndRegisterGlobal();
    }
}
