package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.providers.ProviderConflict;
import io.javelin.providers.ProviderManifest;
import io.javelin.providers.ProviderRegistry;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@CommandLine.Command(name = "provider:list", description = "List discovered providers and conflicts")
public final class ProviderListCommand extends AbstractCommand {
    public ProviderListCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        ProviderRegistry registry = registry();
        if (registry.manifests().isEmpty()) {
            context.output().info("No providers discovered in modules/ or on the classpath.");
            return;
        }
        Set<String> enabledNames = Set.copyOf(registry.enabled().stream()
                .map(ProviderManifest::name).toList());
        for (ProviderManifest manifest : registry.manifests()) {
            String status = enabledNames.contains(manifest.name()) ? "enabled" : "disabled";
            context.output().line(manifest.name() + " " + manifest.version() + " [" + status + "] "
                    + manifest.providerClass() + " depends=" + manifest.dependencies());
        }
        List<ProviderConflict> conflicts = registry.conflicts();
        if (conflicts.isEmpty()) {
            context.output().success("No conflicts found");
        } else {
            conflicts.forEach(conflict -> context.output().line("CONFLICT " + conflict));
        }
    }

    private ProviderRegistry registry() {
        if (context.application().isPresent() && context.application().get().has(ProviderRegistry.class)) {
            return context.application().get().make(ProviderRegistry.class);
        }
        return new ProviderRegistry(Path.of("modules"), List.of(), List.of(), false);
    }
}
