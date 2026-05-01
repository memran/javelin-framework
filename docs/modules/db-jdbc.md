# Database JDBC Module

Artifact: `io.javelin:javelin-db-jdbc`

`javelin-db-jdbc` provides a HikariCP-backed JDBC database implementation, a YAML migration runner, and a service provider for core wiring.

## Public API

- `JdbcDatabase`
- `JdbcQueryBuilder`
- `MigrationRunner`
- `JdbcDatabaseServiceProvider`

## Example

```java
Database db = app.make(Database.class);

db.transaction(database -> {
    return database.table("users").where("id", 1).first();
});
```

## Function Usage

- `JdbcDatabase` is the concrete JDBC-backed `Database` implementation.
- `Database.table(name)` starts a fluent query for a table.
- `QueryBuilder` methods are used to add filters and fetch results.
- `Database.transaction(callback)` wraps multiple operations in one transaction.
- `MigrationRunner` reads YAML files from `database/migrations`, executes `up` statements in order, and records applied files in `javelin_migrations`.
- `MigrationRunner.rollback()` replays the `down` statements from the latest batch.
- `JdbcDatabaseServiceProvider` binds the database into the container.

## Notes

- Uses prepared statements for values.
- Keep table and column names validated or framework-controlled.
- Migration files are YAML documents with `name`, `up`, and `down` keys.
- The console generator can scaffold `create-table`, `add-column`, and `seed` migration shapes while still leaving the file editable.
