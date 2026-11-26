# Mail Module

Artifact: `io.javelin:javelin-mail`

`javelin-mail` provides SMTP delivery, Pebble-rendered messages, attachments/inline images, named transports, queue-backed delivery and a development test transport.

## Developer API

```java
import io.javelin.mails.Mail;
import java.util.Map;

Mail.to("user@example.com")
    .subject("Welcome")
    .view("emails/welcome", Map.of("name", "Ada"))
    .queue();
```

The fluent composer supports `to`, `cc`, `bcc`, `from`, `subject`, `text`, `html`, `view`, `attach` and `transport`. `view(...)` renders the named Pebble template through the registered `ViewRenderer`. `queue()` dispatches a `mail.message` job when a queue is active; otherwise it sends immediately. `send()` always sends immediately.

```java
Mail.to("user@example.com")
    .subject("Report")
    .text("Please find the report attached.")
    .attach(Attachment.file("report.pdf", "application/pdf", bytes))
    .attach(Attachment.image("logo", "image/png", logoBytes))   // reference as cid:logo in HTML
    .transport("secondary")
    .send();
```

## Address validation

`EmailAddress.isValid(value)` and `EmailAddress.requireValid(value)` are applied to every address added to a composer.

## Configuration

```yaml
mail:
  driver: smtp            # or "test" for development
  from: no-reply@example.com
  test:
    dir: storage/mail
  smtp:
    default:
      host: ${SMTP_HOST:}
      port: 587
      username: ${SMTP_USERNAME:}
      password: ${SMTP_PASSWORD:}
      auth: true
      starttls: true
      ssl: false
      timeout_ms: 5000
    secondary:
      host: smtp2.example.com
      port: 465
      ssl: true
  retry:
    max_attempts: 3
    backoff_ms: 1000
```

Each `mail.smtp.<name>` block creates a named `SmtpTransport`. The generated default is `driver: test` so local apps never send real mail; switch to `smtp` in production.

## Queued delivery and failures

`MailServiceProvider` registers a handler for subject `mail.message` in the existing `JobHandlerRegistry`. Failures are retried using `mail.retry.*` through the queue's retry policy, backoff, timeout, failed-job store and replay API.

## Logging

`MailSubmissionLogger` records only recipient count, a masked first recipient, the transport name and attachment count. Message bodies, attachments, addresses in full and SMTP credentials are never logged.

## Test transport

With `mail.driver: test`, each message is written to `mail.test.dir` as a readable `.mail` file containing headers, text/HTML bodies and attachment names. This is the default for generated apps and the demo app.
