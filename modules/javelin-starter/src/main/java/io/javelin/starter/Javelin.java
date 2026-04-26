package io.javelin.starter;

import io.javelin.config.DotenvEnv;
import io.javelin.config.YamlConfig;
import io.javelin.cache.CacheServiceProvider;
import io.javelin.console.ConsoleServiceProvider;
import io.javelin.core.Application;
import io.javelin.core.ConsoleKernel;
import io.javelin.core.ProviderLoader;
import io.javelin.db.jdbc.JdbcDatabaseServiceProvider;
import io.javelin.http.jdk.JdkHttpServiceProvider;
import io.javelin.log.slf4j.Slf4jLogServiceProvider;
import io.javelin.security.SecurityServiceProvider;
import io.javelin.view.pebble.PebbleViewServiceProvider;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;

public final class Javelin {
    private Javelin() {
    }

    public static Application create(Path root) {
        var env = DotenvEnv.load(root.resolve(".env"));
        var config = YamlConfig.load(root.resolve("config/app.yaml"), env);
        Application app = new Application(config, env);
        app.instance(Path.class, root);
        app.register(new Slf4jLogServiceProvider());
        app.register(new JdkHttpServiceProvider());
        app.register(new PebbleViewServiceProvider());
        app.register(new JdbcDatabaseServiceProvider());
        app.register(new SecurityServiceProvider());
        app.register(new CacheServiceProvider());
        app.register(new ConsoleServiceProvider());
        new ProviderLoader().load(app);
        return app;
    }

    public static int run(Class<?> mainClass, String[] args) {
        Application app = create(resolveRoot(mainClass));
        return app.make(ConsoleKernel.class).run(args);
    }

    static Path resolveRoot(Class<?> mainClass) {
        String override = System.getProperty("javelin.root");
        if (override != null && !override.isBlank()) {
            return Path.of(override).toAbsolutePath().normalize();
        }

        Path codeSource = codeSource(mainClass);
        if (codeSource != null) {
            Path appRoot = locateAppRoot(codeSource);
            if (appRoot != null) {
                return appRoot;
            }
            return codeSource;
        }

        return Path.of(System.getProperty("user.dir")).toAbsolutePath().normalize();
    }

    private static Path codeSource(Class<?> mainClass) {
        if (mainClass == null || mainClass.getProtectionDomain() == null) {
            return null;
        }
        CodeSource source = mainClass.getProtectionDomain().getCodeSource();
        if (source == null || source.getLocation() == null) {
            return null;
        }
        try {
            Path path = Path.of(source.getLocation().toURI()).toAbsolutePath().normalize();
            return Files.isRegularFile(path) ? path.getParent() : path;
        } catch (URISyntaxException | IllegalArgumentException exception) {
            return null;
        }
    }

    private static Path locateAppRoot(Path codeSource) {
        Path current = codeSource.toAbsolutePath().normalize();
        for (int i = 0; i < 6 && current != null; i++) {
            if (Files.exists(current.resolve("config/app.yaml"))) {
                return current;
            }
            current = current.getParent();
        }
        return null;
    }
}
