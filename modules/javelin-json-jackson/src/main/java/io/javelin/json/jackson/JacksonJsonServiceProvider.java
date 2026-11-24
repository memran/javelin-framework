package io.javelin.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.javelin.core.Application;
import io.javelin.core.Json;
import io.javelin.core.JsonMapper;
import io.javelin.core.ServiceProvider;

import java.text.SimpleDateFormat;

/** Installs a configured Jackson mapper as the active JSON implementation. */
public final class JacksonJsonServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(JacksonJsonConfig.class, () -> JacksonJsonConfig.from(app.config()));
        app.singleton(ObjectMapper.class, () -> {
            JacksonJsonConfig config = app.make(JacksonJsonConfig.class);
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.INDENT_OUTPUT, config.indentOutput());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, config.failOnUnknownProperties());
            mapper.setSerializationInclusion(
                    config.writeNulls() ? JsonInclude.Include.ALWAYS : JsonInclude.Include.NON_NULL);
            if (!config.dateFormat().isBlank()) {
                mapper.setDateFormat(new SimpleDateFormat(config.dateFormat()));
            }
            return mapper;
        });
        app.singleton(JacksonJsonMapper.class, () -> new JacksonJsonMapper(app.make(ObjectMapper.class)));
        app.singleton(JsonMapper.class, () -> app.make(JacksonJsonMapper.class));
    }

    @Override
    public void boot(Application app) {
        Json.use(app.make(JsonMapper.class));
    }
}
