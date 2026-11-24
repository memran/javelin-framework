package io.javelin.db.jdbc;

import io.javelin.core.Database;
import io.javelin.db.migration.MigrationDialect;

import java.nio.file.Path;

/**
 * Backward-compatible alias for {@link io.javelin.db.migration.MigrationRunner}.
 *
 * @deprecated migrations live in {@code javelin-db-migration}; use the class in
 *             {@code io.javelin.db.migration} directly.
 */
@Deprecated(forRemoval = false)
public class MigrationRunner extends io.javelin.db.migration.MigrationRunner {
    public MigrationRunner(Database database, Path migrationsDirectory) {
        super(database, migrationsDirectory);
    }

    public MigrationRunner(Database database, Path migrationsDirectory, MigrationDialect dialect) {
        super(database, migrationsDirectory, dialect);
    }
}
