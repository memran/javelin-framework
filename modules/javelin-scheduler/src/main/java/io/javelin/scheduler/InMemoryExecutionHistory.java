package io.javelin.scheduler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Process-local history. */
public final class InMemoryExecutionHistory implements ExecutionHistory {
    private final List<ExecutionRecord> records = new ArrayList<>();

    @Override
    public synchronized void record(ExecutionRecord execution) {
        records.add(execution);
    }

    @Override
    public synchronized List<ExecutionRecord> recent(int limit) {
        return records.stream()
                .sorted(Comparator.comparing(ExecutionRecord::startedAt).reversed())
                .limit(Math.max(0, limit))
                .toList();
    }
}
