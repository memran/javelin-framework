package io.javelin.console;

import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(name = "make:provider", description = "Create a service provider")
public final class MakeProviderCommand implements Runnable {
    @CommandLine.Parameters(index = "0")
    String name;

    @Override
    public void run() {
        GeneratorSupport.write(Path.of("app/providers/" + name + ".java"), """
                package app.providers;

                import io.javelin.core.Application;
                import io.javelin.core.ServiceProvider;

                public final class %s implements ServiceProvider {
                    @Override
                    public void register(Application app) {
                    }

                    @Override
                    public void boot(Application app) {
                    }
                }
                """.formatted(name));
    }
}
