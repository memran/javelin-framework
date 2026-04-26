package io.javelin.core;

public interface Database extends AutoCloseable {
    QueryBuilder table(String table);

    <T> T transaction(TransactionCallback<T> callback);

    @Override
    void close();
}
