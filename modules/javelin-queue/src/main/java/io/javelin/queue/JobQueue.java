package io.javelin.queue;

import io.javelin.core.queue.Job;
import io.javelin.core.queue.Queue;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/** Extended queue contract with delayed dispatch, leases and failed-job replay. */
public interface JobQueue extends Queue {
    void dispatch(Job job);

    void later(Job job, Duration delay);

    Optional<ClaimedJob> claim(String workerId, Duration lease);

    void ack(String id, String leaseToken);

    void fail(String id, String leaseToken, String error, Duration retryDelay);

    List<Job> failedJobs();

    void replay(String failedId);

    long pendingCount();

    /** One claimed job plus the token that authorizes ack/fail. */
    record ClaimedJob(String id, Job job, String leaseToken) {
    }
}
