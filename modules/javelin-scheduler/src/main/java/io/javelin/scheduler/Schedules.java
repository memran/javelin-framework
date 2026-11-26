package io.javelin.scheduler;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;

/** Built-in schedule types. */
public final class Schedules {
    private Schedules() {
    }

    public record Interval(Duration every) implements ScheduleSpec {
        @Override
        public Instant nextRun(Instant after, ZoneId zone) {
            return after.plus(every);
        }

        @Override
        public String description() {
            return "every " + every;
        }
    }

    public record Daily(LocalTime at) implements ScheduleSpec {
        @Override
        public Instant nextRun(Instant after, ZoneId zone) {
            ZonedDateTime now = ZonedDateTime.ofInstant(after, zone);
            ZonedDateTime candidate = ZonedDateTime.of(now.toLocalDate(), at, zone);
            return (candidate.isAfter(now) ? candidate : candidate.plusDays(1)).toInstant();
        }

        @Override
        public String description() {
            return "daily at " + at;
        }
    }

    public record Weekly(DayOfWeek day, LocalTime at) implements ScheduleSpec {
        @Override
        public Instant nextRun(Instant after, ZoneId zone) {
            ZonedDateTime now = ZonedDateTime.ofInstant(after, zone);
            ZonedDateTime candidate = ZonedDateTime.of(
                    now.toLocalDate().with(TemporalAdjusters.nextOrSame(day)), at, zone);
            return (candidate.isAfter(now) ? candidate : candidate.plusWeeks(1)).toInstant();
        }

        @Override
        public String description() {
            return "weekly on " + day + " at " + at;
        }
    }

    public record Monthly(int dayOfMonth, LocalTime at) implements ScheduleSpec {
        @Override
        public Instant nextRun(Instant after, ZoneId zone) {
            ZonedDateTime now = ZonedDateTime.ofInstant(after, zone);
            LocalDate date = now.toLocalDate().withDayOfMonth(Math.min(dayOfMonth,
                    now.toLocalDate().lengthOfMonth()));
            ZonedDateTime candidate = ZonedDateTime.of(date, at, zone);
            return (candidate.isAfter(now) ? candidate : nextMonth(candidate, dayOfMonth)).toInstant();
        }

        private ZonedDateTime nextMonth(ZonedDateTime from, int day) {
            LocalDate next = from.toLocalDate().plusMonths(1).withDayOfMonth(1);
            return ZonedDateTime.of(next.withDayOfMonth(Math.min(day, next.lengthOfMonth())),
                    from.toLocalTime(), from.getZone());
        }

        @Override
        public String description() {
            return "monthly on day " + dayOfMonth + " at " + at;
        }
    }

    public record Cron(CronExpression expression) implements ScheduleSpec {
        @Override
        public Instant nextRun(Instant after, ZoneId zone) {
            return expression.nextRun(after, zone);
        }

        @Override
        public String description() {
            return "cron " + expression.expression();
        }
    }
}
