package io.javelin.log.slf4j;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggingEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class JsonLogEncoderTest {
    @Test
    void encodesAValidJsonObjectWithMdcAndException() throws Exception {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        LoggingEvent event = new LoggingEvent("test",
                context.getLogger("io.javelin.test"), Level.INFO, "hello {}",
                new IllegalStateException("boom"), new Object[] {"world"});
        event.setTimeStamp(1_700_000_000_000L);
        event.setThreadName("main");
        event.setMDCPropertyMap(Map.of("request_id", "abc-123"));

        String json = new String(new JsonLogEncoder().encode(event), StandardCharsets.UTF_8);
        Map<?, ?> parsed = new ObjectMapper().readValue(json, Map.class);

        assertEquals("hello world", parsed.get("message"));
        assertEquals("INFO", parsed.get("level"));
        assertEquals("io.javelin.test", parsed.get("logger"));
        assertEquals(Map.of("request_id", "abc-123"), parsed.get("mdc"));
        assertTrue(((String) parsed.get("exception")).contains("IllegalStateException: boom"));
        assertTrue(json.endsWith("\n"));
    }

    @Test
    void escapesQuotesAndControlCharacters() throws Exception {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        LoggingEvent event = new LoggingEvent("test", context.getLogger("test"),
                Level.WARN, "quote \" and tab \t", null, null);

        String json = new String(new JsonLogEncoder().encode(event), StandardCharsets.UTF_8);
        Map<?, ?> parsed = new ObjectMapper().readValue(json, Map.class);

        assertEquals("quote \" and tab \t", parsed.get("message"));
    }
}
