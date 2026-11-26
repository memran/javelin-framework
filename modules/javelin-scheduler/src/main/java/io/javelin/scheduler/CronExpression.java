package io.javelin.scheduler;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.BitSet;
import java.util.Locale;

/**
 * Small dependency-free five-field cron parser: minute hour day-of-month month day-of-week.
 * Supports *, lists, ranges and steps. Day-of-month and day-of-week use OR semantics when both
 * are restricted, matching common cron behavior.
 */
public final class CronExpression {
    private final String expression;
    private final BitSet minutes;
    private final BitSet hours;
    private final BitSet days;
    private final BitSet months;
    private final BitSet weekdays;
    private final boolean dayRestricted;
    private final boolean weekdayRestricted;

    public CronExpression(String expression) {
        this.expression = expression;
        String[] parts = expression.trim().split("\\s+");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Cron expression must have 5 fields: " + expression);
        }
        this.minutes = parse(parts[0], 0, 59);
        this.hours = parse(parts[1], 0, 23);
        this.days = parse(parts[2], 1, 31);
        this.months = parse(parts[3], 1, 12);
        this.weekdays = parse(parts[4], 0, 7);
        this.dayRestricted = !parts[2].equals("*");
        this.weekdayRestricted = !parts[4].equals("*");
    }

    public String expression() {
        return expression;
    }

    public Instant nextRun(Instant after, ZoneId zone) {
        ZonedDateTime candidate = ZonedDateTime.ofInstant(after, zone)
                .plusMinutes(1)
                .truncatedTo(ChronoUnit.MINUTES);
        for (int minute = 0; minute < 60 * 24 * 366; minute++) {
            if (matches(candidate)) {
                return candidate.toInstant();
            }
            candidate = candidate.plusMinutes(1);
        }
        throw new IllegalStateException("No matching cron time within a year: " + expression);
    }

    private boolean matches(ZonedDateTime time) {
        if (!minutes.get(time.getMinute()) || !hours.get(time.getHour()) || !months.get(time.getMonthValue())) {
            return false;
        }
        boolean dayMatch = days.get(time.getDayOfMonth());
        boolean weekdayMatch = weekdays.get(weekday(time.getDayOfWeek()));
        if (dayRestricted && weekdayRestricted) {
            return dayMatch || weekdayMatch;
        }
        return dayMatch && weekdayMatch;
    }

    private static int weekday(DayOfWeek day) {
        return day.getValue() % 7;
    }

    private static BitSet parse(String field, int min, int max) {
        BitSet values = new BitSet(max + 1);
        for (String part : field.split(",")) {
            String segment = part.trim();
            int step = 1;
            int slash = segment.indexOf('/');
            if (slash > 0) {
                step = Integer.parseInt(segment.substring(slash + 1));
                segment = segment.substring(0, slash);
            }
            if (step < 1) {
                throw new IllegalArgumentException("Invalid cron step: " + field);
            }
            int from;
            int to;
            if (segment.equals("*")) {
                from = min;
                to = max;
            } else if (segment.contains("-")) {
                String[] bounds = segment.split("-", 2);
                from = Integer.parseInt(bounds[0]);
                to = Integer.parseInt(bounds[1]);
            } else {
                from = Integer.parseInt(segment);
                to = step == 1 ? from : max;
            }
            if (from < min || to > max || from > to) {
                throw new IllegalArgumentException("Invalid cron field: " + field);
            }
            for (int value = from; value <= to; value += step) {
                values.set(value);
            }
        }
        return values;
    }
}
