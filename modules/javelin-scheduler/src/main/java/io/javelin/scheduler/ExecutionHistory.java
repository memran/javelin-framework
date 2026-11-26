package io.javelin.scheduler;

import java.util.List;

/** Execution history contract. */
public interface ExecutionHistory {
    void record(ExecutionRecord execution);

    List<ExecutionRecord> recent(int limit);
}
