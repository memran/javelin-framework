# Starter Module

Artifact: `io.javelin:javelin-starter`

`javelin-starter` wires the default runtime stack for applications. It is the main Maven dependency most apps should use.

## Public API

- `Javelin`

## Example

```java
public final class Main {
    public static void main(String[] args) {
        System.exit(Javelin.run(Main.class, args));
    }
}
```

## Function Usage

- `Javelin.create(root)` builds the application container for a workspace root and registers the default modules.
- `Javelin.run(mainClass, args)` resolves the workspace root and executes the console kernel.
- Use `Javelin.run(...)` when your app should behave like a normal workspace entry point.
- `ValidationRuleLoader` scans `app/validation/**/*.java` under the workspace root, loads every concrete subclass of `ValidationRule`, and registers them in a `ValidationRuleRegistry`.
- `validation.rules` in `config/app.yaml` is optional and can add extra rule classes that are not under `app/validation`.
- Use an abstract `ValidationRule` base class in `javelin-support` when you want developers to extend a rule in their app folder.
- `javelin new` generates a starter `app/validation/AdultAgeRule.java` example by default.
- Pass `--validation-rule <Name>` to `javelin new` to generate a different starter rule class, such as `TeenAgeRule.java`.

## Notes

- This is the preferred dependency for application and workspace code.
- It composes the core, config, HTTP, logging, views, database, security, cache, and console modules.
