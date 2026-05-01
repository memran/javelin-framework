# Console Module

Artifact: `io.javelin:javelin-console`

`javelin-console` is the developer workspace toolchain. It provides the `javelin` command for scaffolding, local development, build tasks, migrations, diagnostics, and AI-assisted workflows.

## Public API

- `JavelinConsole`
- `ConsoleServiceProvider`
- `ConsoleKernel`
- `CommandRegistry`
- `CommandContext`
- `Command`
- `AbstractCommand`
- `CliException`
- generator helpers under `io.javelin.cli.generator`

## Example

```bash
javelin new crm-app
javelin serve
javelin make:controller UserController
```

## Function Usage

- `javelin new` creates a new workspace with the starter files and install scripts.
- `javelin serve` launches the application in the workspace.
- `javelin make:*` commands generate controllers, models, providers, services, and migrations.
- `javelin migrate` and `javelin migrate:rollback` manage database schema changes.
- `javelin test`, `javelin build`, and `javelin dev` handle local workflow commands.
- `Ai*` commands support explanation, generation, and install workflows.
- `CommandRegistry`, `CommandContext`, and `Command` form the command execution layer.
- `GeneratorSupport` and `template` helpers keep file generation predictable.

## Notes

- The generated workspace is a normal Maven project.
- `javelin-starter` is the runtime dependency for app code.
- The workspace can add any other Maven libraries it needs.
