package io.javelin.mail;

import io.javelin.core.Application;
import io.javelin.core.Json;
import io.javelin.core.ServiceProvider;
import io.javelin.core.queue.RetryPolicy;
import io.javelin.queue.JobHandlerRegistry;

import java.nio.file.Path;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/** Wires transports and the queued-delivery handler. */
public final class MailServiceProvider implements ServiceProvider {
    private static final String PREFIX = "mail.smtp.";

    @Override
    public void register(Application app) {
        app.singleton(MailManager.class, () -> manager(app));
    }

    @Override
    public void boot(Application app) {
        MailManager manager = app.make(MailManager.class);
        io.javelin.mails.Mail.use(manager);
        if (app.has(JobHandlerRegistry.class)) {
            RetryPolicy retry = new RetryPolicy(
                    app.config().getInt("mail.retry.max_attempts", 3),
                    Duration.ofMillis(app.config().getLong("mail.retry.backoff_ms", 1000)));
            app.make(JobHandlerRegistry.class).register("mail.message", job -> {
                MailMessage message = Json.parse(job.payload(), MailMessage.class);
                manager.send(message);
            }, retry);
        }
    }

    private MailManager manager(Application app) {
        String driver = app.config().getString("mail.driver", "smtp");
        String defaultName = app.config().getString("mail.default", "default");
        Map<String, MailTransport> transports = new LinkedHashMap<>();
        if ("test".equalsIgnoreCase(driver)) {
            TestMailTransport transport = new TestMailTransport(
                    Path.of(app.config().getString("mail.test.dir", "storage/mail")),
                    app.config().getString("mail.from", ""));
            transports.put("test", transport);
            transports.put(defaultName, transport);
        } else if ("smtp".equalsIgnoreCase(driver)) {
            for (String name : smtpNames(app)) {
                transports.put(name, new SmtpTransport(SmtpConfig.from(app.config(), name)));
            }
        } else {
            throw new IllegalArgumentException("Unknown mail driver: " + driver);
        }
        return new MailManager(transports, transports.containsKey(defaultName) ? defaultName : "test");
    }

    private Set<String> smtpNames(Application app) {
        Set<String> names = new LinkedHashSet<>();
        for (String key : app.config().keys()) {
            if (key.startsWith(PREFIX)) {
                String rest = key.substring(PREFIX.length());
                int dot = rest.indexOf('.');
                names.add(dot < 0 ? rest : rest.substring(0, dot));
            }
        }
        if (names.isEmpty()) {
            names.add("default");
        }
        return names;
    }
}
