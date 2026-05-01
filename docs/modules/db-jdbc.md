# Database JDBC Module

Artifact: `io.javelin:javelin-db-jdbc`

`javelin-db-jdbc` provides a HikariCP-backed JDBC database implementation and a service provider for core wiring.

## Public API

- `JdbcDatabase`
- `JdbcQueryBuilder`
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
- `JdbcDatabaseServiceProvider` binds the database into the container.

## Notes

- Uses prepared statements for values.
- Keep table and column names validated or framework-controlled.
