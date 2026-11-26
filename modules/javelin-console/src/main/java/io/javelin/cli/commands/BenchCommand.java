package io.javelin.cli.commands;

import io.javelin.cli.AbstractCommand;
import io.javelin.cli.CommandContext;
import io.javelin.deployment.BenchmarkRunner;
import picocli.CommandLine;

import java.time.Duration;

@CommandLine.Command(name = "bench", description = "Run a small HTTP load test")
public final class BenchCommand extends AbstractCommand {
    @CommandLine.Option(names = "--url", defaultValue = "http://127.0.0.1:8080/health")
    String url;
    @CommandLine.Option(names = "--concurrency", defaultValue = "10")
    int concurrency;
    @CommandLine.Option(names = "--seconds", defaultValue = "5")
    int seconds;

    public BenchCommand(CommandContext context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            BenchmarkRunner.Result result = new BenchmarkRunner()
                    .run(url, concurrency, Duration.ofSeconds(seconds));
            context.output().line("URL: " + result.url());
            context.output().line("Requests: " + result.total()
                    + " successes=" + result.successes() + " failures=" + result.failures());
            context.output().line("Throughput: " + String.format("%.1f", result.requestsPerSecond()) + " req/s");
            context.output().line("Latency p50=" + result.p50Ms() + "ms p95=" + result.p95Ms() + "ms");
            context.output().success("Benchmark finished in " + result.durationMs() + "ms");
        } catch (Exception exception) {
            throw new IllegalStateException("Benchmark failed", exception);
        }
    }
}
