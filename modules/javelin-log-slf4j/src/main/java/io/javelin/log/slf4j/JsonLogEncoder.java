package io.javelin.log.slf4j;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.encoder.EncoderBase;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;

/**
 * Small dependency-free JSON Logback encoder. Renders timestamp, level, logger,
 * thread, message, MDC fields and stack traces as one JSON object per line.
 */
public final class JsonLogEncoder extends EncoderBase<ILoggingEvent> {
    @Override
    public byte[] headerBytes() {
        return new byte[0];
    }

    @Override
    public byte[] footerBytes() {
        return new byte[0];
    }

    @Override
    public byte[] encode(ILoggingEvent event) {
        StringBuilder json = new StringBuilder(256).append('{');
        field(json, "timestamp", Instant.ofEpochMilli(event.getTimeStamp()).toString(), true);
        field(json, "level", event.getLevel().levelStr, false);
        field(json, "logger", event.getLoggerName(), false);
        field(json, "thread", event.getThreadName(), false);
        field(json, "message", event.getFormattedMessage(), false);

        Map<String, String> mdc = event.getMDCPropertyMap();
        if (mdc != null && !mdc.isEmpty()) {
            json.append(",\"mdc\":{");
            boolean first = true;
            for (Map.Entry<String, String> entry : mdc.entrySet()) {
                field(json, entry.getKey(), entry.getValue(), first);
                first = false;
            }
            json.append('}');
        }
        if (event.getThrowableProxy() != null) {
            field(json, "exception", ThrowableProxyUtil.asString(event.getThrowableProxy()), false);
        }
        return json.append("}\n").toString().getBytes(StandardCharsets.UTF_8);
    }

    private static void field(StringBuilder json, String name, String value, boolean first) {
        if (!first) {
            json.append(',');
        }
        json.append('"').append(escape(name)).append("\":\"").append(escape(value)).append('"');
    }

    private static String escape(String value) {
        String safe = value == null ? "" : value;
        StringBuilder out = new StringBuilder(safe.length() + 8);
        for (int index = 0; index < safe.length(); index++) {
            char character = safe.charAt(index);
            switch (character) {
                case '"' -> out.append("\\\"");
                case '\\' -> out.append("\\\\");
                case '\n' -> out.append("\\n");
                case '\r' -> out.append("\\r");
                case '\t' -> out.append("\\t");
                default -> {
                    if (character < 0x20) {
                        out.append(String.format("\\u%04x", (int) character));
                    } else {
                        out.append(character);
                    }
                }
            }
        }
        return out.toString();
    }
}
