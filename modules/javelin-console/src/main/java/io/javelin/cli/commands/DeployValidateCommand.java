package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.deployment.ProductionValidator;
import io.javelin.deployment.ValidationIssue;
import picocli.CommandLine;

@CommandLine.Command(name = "deploy:validate", description = "Validate production configuration")
public final class DeployValidateCommand extends AbstractCommand {
    public DeployValidateCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        if (context.application().isEmpty()) {
            context.output().line("WARN config: no application context available");
            return;
        }
        var app = context.application().get();
        for (ValidationIssue issue : new ProductionValidator().validate(app.config(), app.env())) {
            context.output().line(issue.toString());
        }
        context.output().success("Production configuration check complete");
    }
}
