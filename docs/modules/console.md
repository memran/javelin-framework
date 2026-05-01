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
- `javelin make:*` commands generate controllers, models, providers, services, and YAML migrations.
- `javelin make:controller` scaffolds a controller that shows `Request.saveFile(...)` and a model/database handoff for multipart uploads.
- `javelin make:migration` creates a timestamped `.yml` file under `database/migrations`.
- `javelin make:migration --type create-table --table users` scaffolds a table creation migration.
- `javelin make:migration --type add-column --table users --column avatar_path` scaffolds a schema alteration migration.
- `javelin make:migration --type seed --table users` scaffolds a seed-style data migration.
- `javelin migrate` and `javelin migrate:rollback` manage database schema changes through the JDBC migration runner.
- `javelin test`, `javelin build`, and `javelin dev` handle local workflow commands.
- `Ai*` commands use the support `Ai` client for OpenAI-compatible chat and streaming workflows.
- `javelin ai:install` writes `.javelin/ai.properties` with explicit base URL, path, model, and timeout keys.
- `javelin ai:chat` accepts a prompt and can stream tokens with `--stream`.
- `javelin ai:explain` uses the same client when AI config exists and falls back to a local summary otherwise.
- `CommandRegistry`, `CommandContext`, and `Command` form the command execution layer.
- `GeneratorSupport` and `template` helpers keep file generation predictable.

## Notes

- The generated workspace is a normal Maven project.
- `javelin-starter` is the runtime dependency for app code.
- The workspace can add any other Maven libraries it needs.
