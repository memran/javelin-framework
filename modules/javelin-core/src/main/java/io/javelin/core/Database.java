package io.javelin.core;

public interface Database extends AutoCloseable {
    QueryBuilder table(String table);

    default int execute(String sql) {
        throw new UnsupportedOperationException("Raw SQL execution is not supported");
    }

    <T> T transaction(TransactionCallback<T> callback);

    @Override
    void close();
}
