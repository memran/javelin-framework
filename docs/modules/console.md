# Console Module

Artifact: `io.javelin:javelin-console`

`javelin-console` is the developer workspace toolchain. It provides the `javelin` command for scaffolding, local development, build tasks, migrations, diagnostics, and AI-assisted workflows.

## CLI Commands

The `javelin` CLI provides workspace development commands. Run them from your project root:

```bash
# Create a new workspace
javelin new crm-app

# Development server with hot reload
javelin dev

# Start production server
javelin serve

# Scaffold code
javelin make:controller UserController
javelin make:model User
javelin make:service UserService
javelin make:provider CacheProvider
javelin make:module PaymentModule
javelin make:migration create-users-table
javelin make:migration --type create-table --table users
javelin make:migration --type add-column --table users --column avatar_path
javelin make:migration --type seed --table users

# Database migrations
javelin migrate
javelin migrate:rollback

# Development workflow
javelin test
javelin build
javelin native     # build native image
javelin seed       # run database seeds

# Diagnostics
javelin routes:list
javelin doctor
javelin version

# Queue
javelin queue:work
javelin queue:status

# Scheduling
javelin schedule:run

# Caching
javelin cache:clear
javelin config:cache

# AI-assisted commands (requires ai.properties)
javelin ai:install
javelin ai:chat "explain this error"
javelin ai:chat --stream "write a controller"
javelin ai:generate "create a blog module"
javelin ai:explain "what does this route do?"
```

## JavelinConsole

`JavelinConsole` implements `ConsoleKernel` and provides the runtime entry point for dispatching console commands.

```java
JavelinConsole console = new JavelinConsole(app);
int exitCode = console.run(args);

// Or start the HTTP server directly:
JavelinConsole.start(app);
```

- `new JavelinConsole(Application app)` -- creates a console kernel for the given application.
- `console.run(String[] args)` -- parses CLI arguments and dispatches to the matching subcommand. Returns an exit code (0 for success).
- `console.app()` -- returns the underlying `Application` instance.
- `JavelinConsole.start(Application app)` -- static helper that boots the app (if not already booted) and starts the HTTP server via `app.make(HttpServerAdapter.class).start()`.

## ConsoleServiceProvider

`ConsoleServiceProvider` registers `JavelinConsole` as the `ConsoleKernel` implementation.

```java
ConsoleServiceProvider provider = new ConsoleServiceProvider();
provider.register(app);
```

- `provider.register(app)` -- reads available commands from config and registers `JavelinConsole` as `ConsoleKernel`.

```java
// After registration:
ConsoleKernel kernel = app.make(ConsoleKernel.class);
int code = kernel.run(args);
```

## ConsoleKernel (cli package)

`io.javelin.cli.ConsoleKernel` is the actual command dispatcher used by `JavelinConsole`.

```java
ConsoleKernel kernel = new ConsoleKernel(application);
int code = kernel.run(args);

// Or with a pre-built context:
ConsoleKernel kernel = new ConsoleKernel(context);
kernel.run(); // shows usage
```

- `new ConsoleKernel(Application app)` -- creates a kernel that resolves commands from the application container.
- `new ConsoleKernel(CommandContext context)` -- creates a kernel with a custom context.
- `kernel.run(String[] args)` -- parses arguments and dispatches the command. Returns exit code.
- `kernel.run()` -- shows usage/help text (from `Runnable` interface).

## CommandRegistry

`CommandRegistry` manages the set of available CLI commands.

```java
CommandRegistry registry = new CommandRegistry();
registry.register("greet", () -> new GreetCommand(context));

Set<String> names = registry.names(); // ["greet", "serve", ...]
Object cmd = registry.create("greet"); // creates a fresh instance
```

- `registry.register(String name, Supplier<Object> factory)` -- registers a command by name with a factory that creates a new instance.
- `registry.names()` -- returns the set of registered command names.
- `registry.create(String name)` -- creates a fresh command instance by name.

## CommandContext

`CommandContext` carries the application, working directory, and output writer through command execution.

```java
CommandContext ctx = new CommandContext(
    app,
    Path.of("."),
    new ConsoleOutput()
);

Optional<Application> app = ctx.application();
Application app = ctx.requireApplication(); // throws if null
Path cwd = ctx.workingDirectory();
ConsoleOutput out = ctx.output();
```

- `new CommandContext(Application, Path, ConsoleOutput)` -- creates a context with all dependencies.
- `ctx.application()` -- returns `Optional<Application>`.
- `ctx.requireApplication()` -- returns `Application` or throws if none.
- `ctx.workingDirectory()` -- returns the working directory path.
- `ctx.output()` -- returns the `ConsoleOutput` instance.

## Command

`Command` is the interface for all CLI commands.

```java
public interface Command extends Runnable {
    String name();
}
```

```java
public class GreetCommand implements Command {
    private final CommandContext ctx;

    public GreetCommand(CommandContext ctx) {
        this.ctx = ctx;
    }

    public String name() {
        return "greet";
    }

    public void run() {
        ctx.output().success("Hello, Javelin!");
    }
}
```

## AbstractCommand

`AbstractCommand` is a base class for commands that provides the `name()` implementation from picocli's `@CommandLine.Command` annotation.

```java
@CommandLine.Command(name = "greet", description = "Prints a greeting")
public final class GreetCommand extends AbstractCommand {
    public GreetCommand(CommandContext context) {
        super(context);
    }

    public void run() {
        output().success("Hello!");
    }
}
```

## CliException

`CliException` is a runtime exception for CLI errors with an optional fix suggestion.

```java
throw new CliException("Could not find workspace root");
throw new CliException("Missing config file", "Run 'javelin new' first");

String message = exception.getMessage();
String fix = exception.fix(); // may be null
```

## ConsoleOutput

`ConsoleOutput` provides colored terminal output.

```java
ConsoleOutput out = new ConsoleOutput();
ConsoleOutput out = new ConsoleOutput(new PrintWriter(System.out), true); // with ANSI

out.success("Operation completed");
out.info("Processing...");
out.warn("Deprecated usage");
out.error("Something failed");
out.line("Plain message");
```

- `new ConsoleOutput()` -- creates output writing to `System.out` with ANSI colors enabled.
- `new ConsoleOutput(PrintWriter, boolean ansi)` -- creates output with a custom writer and ANSI setting.
- `out.success(message)` -- prints a green success message.
- `out.info(message)` -- prints a blue informational message.
- `out.warn(message)` -- prints a yellow warning message.
- `out.error(message)` -- prints a red error message.
- `out.line(message)` -- prints a plain message without decoration.

## Generator Helpers

### Names

`Names` provides naming conventions for generated files.

```java
String className = Names.className("user_controller", "Controller"); // "UserController"
String packageName = Names.packageName("UserController", "app"); // "app"
String snake = Names.snake("UserController"); // "user_controller"
```

- `Names.className(String raw, String suffix)` -- converts a raw name to a class name with the given suffix.
- `Names.packageName(String raw, String rootPackage)` -- converts a raw name to a package name.
- `Names.snake(String raw)` -- converts a name to snake_case.

### FileGenerator

`FileGenerator` generates files from templates.

```java
FileGenerator generator = new FileGenerator(new TemplateEngine());

// Write a file from a template
Path path = generator.write(
    Path.of("src/main/java/app/"),
    "Controller.java",
    Map.of("className", "UserController"),
    false // don't overwrite
);

// Create a directory
generator.directory(Path.of("src/main/java/app/"));
```

- `new FileGenerator(TemplateEngine)` -- creates a generator with the given template engine.
- `generator.write(target, template, values, force)` -- renders a template and writes it to the target path. Returns the written path. Throws if the file exists and `force` is false.
- `generator.directory(target)` -- creates the directory and any parent directories.

### TemplateEngine

`TemplateEngine` renders template strings by replacing `{{key}}` placeholders.

```java
TemplateEngine engine = new TemplateEngine();
String result = engine.render("Hello {{name}}!", Map.of("name", "Alice"));
// "Hello Alice!"
```

## ProcessTasks

`ProcessTasks` runs external processes for workflow commands like `test`, `build`, `serve`, etc.

```java
ProcessTasks.run(context, "build", List.of("mvn", "clean", "package"));
```

- `ProcessTasks.run(CommandContext, String task, List<String> command)` -- runs a command in a subprocess, streams output, and fails with a descriptive error if the process exits non-zero.

## Notes

- The generated workspace is a normal Maven project.
- `javelin-starter` is the runtime dependency for app code.
- The workspace can add any other Maven libraries it needs.
- `javelin new` creates a complete project structure with install scripts, a Docker Compose stack (app + PostgreSQL), Tailwind CSS and Alpine.js sources, and npm/bun build scripts that emit static assets into `public/`.
- AI commands require `.javelin/ai.properties` (generated by `ai:install`).
