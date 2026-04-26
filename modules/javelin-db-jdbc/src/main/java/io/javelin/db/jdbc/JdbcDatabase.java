package io.javelin.db.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javelin.core.Database;
import io.javelin.core.QueryBuilder;
import io.javelin.core.TransactionCallback;

import javax.sql.DataSource;
import java.sql.Connection;

public final class JdbcDatabase implements Database {
    private final HikariDataSource dataSource;
    private final ThreadLocal<Connection> transactionConnection = new ThreadLocal<>();

    public JdbcDatabase(HikariConfig config) {
        this.dataSource = new HikariDataSource(config);
    }

    public DataSource dataSource() {
        return dataSource;
    }

    Connection connection() throws java.sql.SQLException {
        Connection current = transactionConnection.get();
        return current != null ? current : dataSource.getConnection();
    }

    boolean inTransaction() {
        return transactionConnection.get() != null;
    }

    @Override
    public QueryBuilder table(String table) {
        return new JdbcQueryBuilder(this, table);
    }

    @Override
    public <T> T transaction(TransactionCallback<T> callback) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            transactionConnection.set(connection);
            try {
                T result = callback.run(this);
                connection.commit();
                return result;
            } catch (Exception exception) {
                connection.rollback();
                throw new IllegalStateException("Transaction failed", exception);
            } finally {
                transactionConnection.remove();
                connection.setAutoCommit(true);
            }
        } catch (java.sql.SQLException exception) {
            throw new IllegalStateException("Unable to start transaction", exception);
        }
    }

    @Override
    public void close() {
        dataSource.close();
    }
}
