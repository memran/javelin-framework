package io.javelin.cli.generator;

import io.javelin.cli.CliException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class TemplateEngine {
    public String render(String template, Map<String, String> values) {
        String content = load(template);
        for (Map.Entry<String, String> entry : values.entrySet()) {
            content = content.replace("{{ " + entry.getKey() + " }}", entry.getValue());
        }
        return content;
    }

    private String load(String template) {
        String path = "/stubs/" + template;
        try (InputStream stream = TemplateEngine.class.getResourceAsStream(path)) {
            if (stream == null) {
                throw new CliException("Missing template: " + template);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new CliException("Unable to read template: " + template);
        }
    }
}
