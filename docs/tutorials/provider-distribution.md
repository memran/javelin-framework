# Distributing providers

This tutorial shows how to generate, enable, inspect and install a provider.

## 1. Generate a provider

```bash
javelin make:provider Billing
```

This creates:

- `app/providers/BillingProvider.java` — a `ServiceProvider` skeleton,
- `modules/billing/provider.json` — its manifest with name, class, version and compatibility bounds.

Add your bindings in the provider's `register(Application app)` method, for example `app.singleton(InvoiceService.class, InvoiceService::new)`.

## 2. Enable or disable it

Generated apps load all enabled manifests by default. To be explicit:

```yaml
provider:
  directory: modules
  strict: false
  enabled: [billing]
  disabled: []
```

`enabled: []` means all manifests. `strict: true` turns any conflict into a startup error.

## 3. Inspect and diagnose

```bash
javelin provider:list
```

Shows each provider's name, version, status, class and dependencies, plus `CONFLICT` lines for duplicates, incompatible versions, missing dependencies or cycles.

## 4. Install a versioned provider JAR

Package a provider JAR containing `META-INF/javelin/provider.json`, install it into Maven, then:

```bash
javelin provider:install com.example:billing:1.2.0
```

The command locates the jar in `~/.m2/repository`, copies it into `modules/billing/`, reads its manifest, adds the Maven dependency to `pom.xml` and appends the provider class to `config/app.yaml`.

## 5. CLI used by a distributed app

```bash
java -jar app.jar serve
java -jar app.jar route:list      # alias of routes:list
java -jar app.jar make:provider BillingProvider
java -jar app.jar provider:list
java -jar app.jar migrate
java -jar app.jar queue:work
java -jar app.jar schedule:run
```

See [Provider Loader](modules/provider-loader.md) for the manifest format and diagnostics.
