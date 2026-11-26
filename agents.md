# Agent Notes

## Architecture Intent

Javelin is not Spring Boot. Keep behavior explicit, modular, and cheap to start. Avoid annotation-heavy discovery, broad reflection, hidden global state, and ORM-style abstractions.

## Coding Principles

- MUST keep classes small, composable, and single-purpose.
- MUST keep public APIs explicit and predictable.
- MUST favor provider registration over automatic scanning or implicit discovery.
- MUST use JDK types and the standard library first when they are enough.
- MUST keep names direct and stable.
- NEVER introduce broad reflection, hidden singletons, or magic configuration parsing.
- NEVER add clever abstractions that make the flow harder to follow.
- PRESERVE backward compatibility for shared APIs unless a breaking change is clearly required.

## Current MVP Boundaries

- `javelin-core` owns contracts, container, providers, routing, request/response, kernel, and facades.
- Runtime integrations live in adapter modules.
- `javelin-starter` wires the default stack.
- Application routes are loaded through configured `ServiceProvider` classes.

## Tests Principles

- MUST add tests whenever shared contracts, request flow, database behavior, generators, or service-provider wiring changes.
- MUST prefer focused unit tests for contracts and adapters.
- MUST add integration-style tests when changes cross module boundaries.
- MUST keep test fixtures minimal and explicit.
- MUST test the public behavior that consumers rely on, not private implementation details.
- MUST verify generated file contents and command results when generated output changes.
- MUST keep tests deterministic and fast enough to run with the normal module suite.

## Project Guidelines

- MUST keep module boundaries clear. Core owns contracts; adapters implement them; starter wires defaults.
- NEVER add Spring, Hibernate, or annotation scanning.
- MUST keep security defaults on in starter wiring.
- MUST prefer explicit configuration keys and documented defaults.
- MUST keep generated project templates aligned with the runtime API.
- MUST update docs alongside code when public usage changes.
- MUST refresh graphify after source, docs, or repository structure changes.

## Architect Guidelines

- MUST design around explicit composition, not framework magic.
- MUST keep startup cheap and dependency graphs shallow.
- MUST add new behavior behind interfaces or service providers when possible.
- NEVER add hidden cross-module coupling.
- MUST treat runtime adapters as replaceable boundaries.
- MUST keep model and helper APIs lightweight; do not drift into ORM-style or framework-heavy behavior.
- MUST make the minimal coherent addition and document the usage path when a feature expands the public shape.

## Engineering Rules

- Prefer interface-first APIs and provider-based registration.
- Keep constructor auto-resolution simple and deterministic.
- Do not introduce Spring, Hibernate, or annotation scanning.
- Use JDK features directly when they are enough.
- Keep security defaults on in starter wiring.
- Add tests when touching shared contracts or request flow.

## Graphify Workflow

- When a question is about this repository, query `graphify` first before answering.
- Prefer `graphify query`, `graphify explain`, or `graphify path` to inspect the existing graph instead of guessing from memory.
- After finishing changes that affect source, docs, or repo structure, refresh the graph with `python -m graphify update .` from the repo root.
- If the change only affects generated graph outputs, do not hand-edit them; regenerate through `graphify`.

## Next Useful Work

- Stream satisfiable byte ranges too, so very large `Range` responses also avoid a `byte[]` buffer.
- Add an OTel SDK/exporter example to the demo app and verify exported spans end to end.
- Extend GraalVM metadata with demo-app-specific providers, DTOs and templates.
- Add broker-backed RabbitMQ/NATS integration tests for failure, replay and delayed delivery.
- Make static-asset precedence configurable and publish aggregated performance history in CI.

## Do / Don’t

### Do

- Do keep changes small and focused.
- Do update tests when public behavior changes.
- Do keep docs and generated stubs aligned with code.
- Do prefer explicit wiring through service providers.
- Do run graphify after repo, source, or doc updates.

### Don’t

- Don’t add broad reflection or hidden discovery.
- Don’t introduce Spring-style or ORM-style behavior.
- Don’t leave generator output and docs out of sync.
- Don’t change shared APIs without checking compatibility.
- Don’t skip tests for shared contracts, adapters, or generators.

## graphify

This project has a knowledge graph at graphify-out/ with god nodes, community structure, and cross-file relationships.

When the user types `/graphify`, use the installed graphify skill or instructions before doing anything else.

Rules:
- When graphify-out/graph.json exists, run `graphify query "<question>"` before your first grep, search or source-file read for a codebase question, and put this rule in every subagent prompt that explores code. Read raw files only for lines the graph or an earlier read already located. Use `graphify path "<A>" "<B>"` for relationships and `graphify explain "<concept>"` for focused concepts. When the answer is under a Markdown heading, `graphify section "<heading>"` prints that section's text, so the .md need not be opened. These return a scoped subgraph, usually much smaller than GRAPH_REPORT.md or raw grep output.
- Dirty graphify-out/ files are expected after hooks or incremental updates; dirty graph files are not a reason to skip graphify. Only skip graphify if the task is about stale or incorrect graph output, or the user explicitly says not to use it.
- If graphify-out/wiki/index.md exists, use it for broad navigation instead of raw source browsing.
- Read graphify-out/GRAPH_REPORT.md only for broad architecture review or when query/path/explain do not surface enough context.
- After modifying code, run `graphify update .` to keep the graph current (AST-only, no API cost).
- A committed graphify-out/graph.json is rewritten whole on every build, so a large diff to it in a pull request is expected. Do not flag it as churn or suggest skipping the graph rebuild or its hooks.
