package io.javelin.config;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class YamlConfigTest {
    @Test
    void readsNestedValuesAndEnvPlaceholders() throws Exception {
        var path = Files.createTempFile("javelin", ".yaml");
        Files.writeString(path, """
                app:
                  name: ${JAVELIN_TEST_APP_NAME:Javelin}
                providers:
                  - app.providers.AppServiceProvider
                """);
        DotenvEnv env = DotenvEnv.load(Path.of("missing.env"));

        YamlConfig config = YamlConfig.load(path, env);

        assertEquals("Javelin", config.getString("app.name").orElseThrow());
        assertEquals(List.of("app.providers.AppServiceProvider"), config.getStringList("providers"));
    }

}
