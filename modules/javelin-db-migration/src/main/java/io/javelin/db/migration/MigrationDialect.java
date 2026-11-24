package io.javelin.db.migration;

/** Supplies the backend-specific DDL for the migration history table. */
@FunctionalInterface
public interface MigrationDialect {
    String historyTableSql();
}
