# Agent Notes

## Architecture Intent

Javelin is not Spring Boot. Keep behavior explicit, modular, and cheap to start. Avoid annotation-heavy discovery, broad reflection, hidden global state, and ORM-style abstractions.

## Current MVP Boundaries

- `javelin-core` owns contracts, container, providers, routing, request/response, kernel, and facades.
- Runtime integrations live in adapter modules.
- `javelin-starter` wires the default stack.
- Application routes are loaded through configured `ServiceProvider` classes.

## Engineering Rules

- Prefer interface-first APIs and provider-based registration.
- Keep constructor auto-resolution simple and deterministic.
- Do not introduce Spring, Hibernate, or annotation scanning.
- Use JDK features directly when they are enough.
- Keep security defaults on in starter wiring.
- Add tests when touching shared contracts or request flow.

## Next Useful Work

- Add migration runner and migration file generator.
- Add multipart upload parsing and static assets.
- Add trusted proxy handling before using forwarded headers.
- Add validation primitives.
- Add integration tests for the JDK HTTP adapter.
