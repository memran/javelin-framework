# Provider Loader Module

Artifact: `io.javelin:javelin-provider-loader`

`javelin-provider-loader` discovers providers from versioned manifests, checks compatibility, reports conflicts and registers enabled providers in dependency order. The older `io.javelin.core.ProviderLoader` (config `providers:` list) remains unchanged.

## Manifest

```json
{
  "name": "billing",
  "providerClass": "app.providers.BillingProvider",
  "version": "1.2.0",
  "minJavelin": "0.1.0",
  "maxJavelin": "",
  "dependencies": ["core"],
  "enabled": true
}
```

Manifests are read from:

- `modules/<name>/provider.json` in the application root (local modules),
- `META-INF/javelin/provider.json` on the classpath (provider JARs).

`make:provider Billing` generates both the provider class and a local manifest.

## Configuration

```yaml
provider:
  directory: modules
  strict: false
  enabled: []
  disabled: []
```

- empty `enabled` means "all manifest providers";
- `enabled`/`disabled` contain manifest names;
- `strict: true` fails startup when any conflict is reported;
- `provider.directory` points at the local modules directory.

The separate `providers:` list remains the explicit core-provider list.

## Diagnostics

`ProviderRegistry.conflicts()` reports:

- duplicate provider names,
- incompatible framework versions (`minJavelin`/`maxJavelin`),
- missing dependencies,
- dependency cycles,
- provider classes that do not implement `ServiceProvider` or cannot be instantiated.

`javelin provider:list` prints every discovered provider with status, version, class and dependencies, followed by any conflicts.

## Distribution

`make:provider` produces a local module. Package it as a versioned JAR containing `META-INF/javelin/provider.json`, publish it to Maven, then install it:

```bash
javelin provider:install billing                    # local modules/billing
javelin provider:install com.example:billing:1.2.0  # jar in ~/.m2
```

The Maven form copies the jar into `modules/<artifact>/`, adds a POM dependency and enables the provider in `config/app.yaml`. Dependency versions are reported rather than silently overridden.

## Service provider

`ProviderRegistryServiceProvider` builds the registry during the register phase, exposes it in the container and registers the enabled providers in dependency order. Generated apps and `demo-app` include it by default.
