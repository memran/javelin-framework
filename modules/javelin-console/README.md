# Javelin CLI

Javelin CLI provides the `javelin` executable for project scaffolding, local development, code generation, build tasks, queues, scheduler hooks, migrations, diagnostics, and AI-assisted workflows.

## Build

```bash
mvn -pl :javelin-console -am package
```

The runnable jar is produced at:

```text
modules/javelin-console/target/javelin-console-0.1.0-SNAPSHOT-all.jar
```

Run it directly:

```bash
java -jar modules/javelin-console/target/javelin-console-0.1.0-SNAPSHOT-all.jar --help
```

Install a global launcher by adding a small shell script or `.cmd` file named `javelin` that runs the jar.

## Core Commands

```bash
javelin new crm-app
javelin make:controller User
javelin make:service Billing
javelin make:model Invoice
javelin make:module Billing
javelin make:migration create_invoices_table
javelin routes:list
javelin serve
javelin dev
javelin test
javelin build
javelin native
javelin doctor
```

## AI Commands

```bash
javelin ai:install
javelin ai:chat
javelin ai:generate module Billing
javelin ai:explain stacktrace.txt
```

The MVP stores local AI configuration and provides deterministic local fallbacks. Provider-backed chat/generation can be connected behind the same command classes without changing the public CLI.

## Native Image

Install GraalVM for Java 25+, then run:

```bash
mvn -pl :javelin-console -am -DskipTests package native:compile
```

The native executable is named `javelin`. The CLI avoids annotation scanning and broad reflection; Picocli command objects are registered explicitly through `CommandRegistry`.

## Architecture

The CLI is organized around explicit command registration:

```text
io.javelin.cli.Command
io.javelin.cli.AbstractCommand
io.javelin.cli.CommandContext
io.javelin.cli.CommandRegistry
io.javelin.cli.ConsoleKernel
```

Generators use resource stubs under `src/main/resources/stubs` and refuse to overwrite files unless `--force` is provided.
