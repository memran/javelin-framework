package io.javelin.json.jackson;

import io.javelin.core.Config;

/** JSON settings read from explicit {@code json.*} keys. */
public record JacksonJsonConfig(boolean indentOutput, boolean failOnUnknownProperties,
                                boolean writeNulls, String dateFormat) {
    public static JacksonJsonConfig from(Config config) {
        return new JacksonJsonConfig(
                config.getBoolean("json.indent_output").orElse(false),
                config.getBoolean("json.fail_on_unknown_properties").orElse(false),
                config.getBoolean("json.write_nulls").orElse(true),
                config.getString("json.date_format", ""));
    }
}
