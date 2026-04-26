package io.javelin.core;

@FunctionalInterface
public interface TransactionCallback<T> {
    T run(Database database) throws Exception;
}
