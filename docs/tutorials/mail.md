# Sending mail

The runnable `demo-app` uses the test transport, so no SMTP server is needed.

## 1. Compose and send

```java
Mail.to("user@example.com")
    .subject("Welcome")
    .view("emails/welcome", Map.of("name", "Ada"))
    .queue();
```

`view(...)` renders `resources/views/emails/welcome.peb` through Pebble. Generated apps ship that template. Try the demo:

```bash
curl 'http://127.0.0.1:8080/demo/automation/mail?to=alice@example.com'
```

The message is queued, processed by the worker, and written to `storage/mail/*.mail`.

## 2. Plain text, attachments and inline images

```java
Mail.to("user@example.com")
    .subject("Report")
    .text("Please find the report attached.")
    .attach(Attachment.file("report.pdf", "application/pdf", bytes))
    .attach(Attachment.image("logo", "image/png", logoBytes))   // <img src="cid:logo">
    .send();
```

## 3. Production SMTP

```yaml
mail:
  driver: smtp
  from: no-reply@example.com
  smtp:
    default:
      host: smtp.example.com
      port: 587
      username: ${SMTP_USERNAME:}
      password: ${SMTP_PASSWORD:}
      auth: true
      starttls: true
    reports:
      host: smtp-reports.example.com
      port: 465
      ssl: true
```

Select a transport with `.transport("reports")`. Credentials come from environment/secret providers, never from source.

## 4. Reliability and safety

- `.queue()` hands delivery to the queue, so retries, backoff, timeouts, failed-job storage and replay all apply.
- Submission logs contain only a masked recipient and metadata — never bodies or credentials.
- Every address is validated when added to a composer.
- Keep `driver: test` in development to inspect generated `.mail` files before going live.

See [Mail](modules/mail.md) for all config keys and API methods.
