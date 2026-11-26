package io.javelin.deployment;

import io.javelin.core.Config;
import io.javelin.core.Env;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Explicit production configuration checks. */
public final class ProductionValidator {
    private static final Set<String> QUEUE_DRIVERS = Set.of(
            "none", "file", "database", "valkey", "redis", "rabbitmq", "nats");

    public List<ValidationIssue> validate(Config config, Env env) {
        List<ValidationIssue> issues = new ArrayList<>();
        String name = config.getString("app.name", "");
        if (name.isBlank()) {
            issues.add(ValidationIssue.error("app.name", "Application name is required"));
        }

        String environment = env.get("APP_ENV").orElseGet(() -> config.getString("app.env", "local"));
        boolean production = "prod".equalsIgnoreCase(environment) || "production".equalsIgnoreCase(environment);

        String queue = config.getString("queue.driver", "none").toLowerCase(Locale.ROOT);
        if (!QUEUE_DRIVERS.contains(queue)) {
            issues.add(ValidationIssue.error("queue.driver", "Unknown queue driver: " + queue));
        }

        if (config.getStringList("security.trusted_proxies").contains("*")) {
            issues.add(ValidationIssue.error("security.trusted_proxies",
                    "Wildcard trust is not allowed; list explicit IPs or CIDRs"));
        }

        if (production) {
            String mail = config.getString("mail.driver", "smtp").toLowerCase(Locale.ROOT);
            if ("test".equals(mail)) {
                issues.add(ValidationIssue.error("mail.driver", "Test mail transport must not be used in production"));
            }
            String secret = config.getString("security.jwt.secret", "");
            if (secret.isBlank() || secret.contains("change-me") || secret.contains("demo-only")) {
                issues.add(ValidationIssue.error("security.jwt.secret",
                        "Set a real signing secret from a secret provider"));
            }
            List<String> origins = config.getStringList("cors.allowed_origins");
            if (origins.contains("*") && config.getBoolean("cors.allow_credentials").orElse(false)) {
                issues.add(ValidationIssue.error("cors", "CORS wildcard cannot be combined with credentials"));
            }
            if (!"database".equalsIgnoreCase(config.getString("scheduler.history", "memory"))) {
                issues.add(ValidationIssue.warning("scheduler.history",
                        "Use database history for multi-instance deployments"));
            }
            if (!"database".equalsIgnoreCase(config.getString("scheduler.lock", "memory"))) {
                issues.add(ValidationIssue.warning("scheduler.lock",
                        "Use database locks for multi-instance deployments"));
            }
        }
        return List.copyOf(issues);
    }
}
