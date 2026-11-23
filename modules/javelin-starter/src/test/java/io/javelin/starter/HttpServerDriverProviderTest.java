package io.javelin.starter;

import io.javelin.core.Application;
import io.javelin.core.Config;
import io.javelin.core.Env;
import io.javelin.core.HttpServerAdapter;
import io.javelin.http.jdk.JdkHttpServer;
import io.javelin.http.netty.NettyHttpServer;
import io.javelin.http.undertow.UndertowHttpServer;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

final class HttpServerDriverProviderTest {
    @Test
    void registersTheSelectedHttpAdapterFromTheStarterPrefix() throws Exception {
        HttpServerAdapter jdk = adapterFor("javelin.starter.http-driver", "jdk");
        HttpServerAdapter netty = adapterFor("javelin.starter.http-driver", "netty");
        HttpServerAdapter undertow = adapterFor("javelin.starter.http-driver", "undertow");
        try {
            assertInstanceOf(JdkHttpServer.class, jdk);
            assertInstanceOf(NettyHttpServer.class, netty);
            assertInstanceOf(UndertowHttpServer.class, undertow);
        } finally {
            jdk.close();
            netty.close();
            undertow.close();
        }
    }

    @Test
    void fallsBackToTheLegacyDriverKeyAndThenToNetty() throws Exception {
        HttpServerAdapter legacy = adapterFor("server.driver", "jdk");
        HttpServerAdapter fallback = adapterFor("unrelated.key", "ignored");
        try {
            assertInstanceOf(JdkHttpServer.class, legacy);
            assertInstanceOf(NettyHttpServer.class, fallback);
        } finally {
            legacy.close();
            fallback.close();
        }
    }

    private static HttpServerAdapter adapterFor(String key, String driver) throws Exception {
        Path root = Files.createTempDirectory("javelin-driver");
        Application app = new Application(new DriverConfig(key, driver), new DriverEnv());
        app.instance(Path.class, root);
        app.register(new HttpServerDriverProvider());
        app.boot();
        return app.make(HttpServerAdapter.class);
    }

    private static final class DriverConfig implements Config {
        private final String key;
        private final String driver;

        private DriverConfig(String key, String driver) {
            this.key = key;
            this.driver = driver;
        }

        @Override
        public Optional<String> getString(String key) {
            return key.equals(this.key) ? Optional.of(driver) : Optional.empty();
        }

        @Override
        public Optional<Integer> getInt(String key) {
            return key.equals("server.port") ? Optional.of(0) : Optional.empty();
        }

        @Override
        public Optional<Boolean> getBoolean(String key) {
            return Optional.empty();
        }

        @Override
        public List<String> getStringList(String key) {
            return List.of();
        }
    }

    private static final class DriverEnv implements Env {
        @Override
        public Optional<String> get(String key) {
            return Optional.empty();
        }
    }
}
