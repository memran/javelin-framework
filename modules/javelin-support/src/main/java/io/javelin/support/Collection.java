package io.javelin.support;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public final class Collection<T> implements Iterable<T> {
    private final List<T> values;

    private Collection(List<T> values) {
        this.values = Collections.unmodifiableList(new ArrayList<>(values));
    }

    @SafeVarargs
    public static <T> Collection<T> of(T... values) {
        if (values == null || values.length == 0) {
            return new Collection<>(List.of());
        }
        return new Collection<>(Arrays.asList(values.clone()));
    }

    public static <T> Collection<T> from(java.util.Collection<? extends T> values) {
        Objects.requireNonNull(values, "values");
        return new Collection<>(new ArrayList<>(values));
    }

    public static <T> Collection<T> empty() {
        return new Collection<>(List.of());
    }

    public List<T> values() {
        return values;
    }

    public int size() {
        return values.size();
    }

    public int count() {
        return size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public T first() {
        return values.isEmpty() ? null : values.get(0);
    }

    public T last() {
        return values.isEmpty() ? null : values.get(values.size() - 1);
    }

    public boolean contains(Object candidate) {
        return values.contains(candidate);
    }

    public boolean containsAny(Object... candidates) {
        Objects.requireNonNull(candidates, "candidates");
        for (Object candidate : candidates) {
            if (contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAll(Object... candidates) {
        Objects.requireNonNull(candidates, "candidates");
        for (Object candidate : candidates) {
            if (!contains(candidate)) {
                return false;
            }
        }
        return true;
    }

    public Collection<T> compact() {
        if (values.isEmpty()) {
            return this;
        }
        List<T> compacted = new ArrayList<>(values.size());
        for (T value : values) {
            if (value != null) {
                compacted.add(value);
            }
        }
        return new Collection<>(compacted);
    }

    public <R> Collection<R> map(Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        List<R> mapped = new ArrayList<>(values.size());
        for (T value : values) {
            mapped.add(mapper.apply(value));
        }
        return new Collection<>(mapped);
    }

    public <R> Collection<R> flatMap(Function<? super T, ? extends Iterable<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        List<R> flattened = new ArrayList<>();
        for (T value : values) {
            Iterable<? extends R> mapped = mapper.apply(value);
            if (mapped == null) {
                continue;
            }
            for (R item : mapped) {
                flattened.add(item);
            }
        }
        return new Collection<>(flattened);
    }

    public Collection<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        List<T> filtered = new ArrayList<>();
        for (T value : values) {
            if (predicate.test(value)) {
                filtered.add(value);
            }
        }
        return new Collection<>(filtered);
    }

    public Collection<T> reject(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return filter(predicate.negate());
    }

    public Collection<T> only(Predicate<? super T> predicate) {
        return filter(predicate);
    }

    public Collection<T> except(Predicate<? super T> predicate) {
        return reject(predicate);
    }

    public Collection<T> unique() {
        if (values.isEmpty()) {
            return this;
        }
        return new Collection<>(new ArrayList<>(new LinkedHashSet<>(values)));
    }

    public Collection<T> reverse() {
        if (values.size() <= 1) {
            return this;
        }
        List<T> reversed = new ArrayList<>(values);
        Collections.reverse(reversed);
        return new Collection<>(reversed);
    }

    public Collection<T> take(int count) {
        requireNonNegative(count, "count");
        if (count == 0 || values.isEmpty()) {
            return empty();
        }
        if (count >= values.size()) {
            return this;
        }
        return new Collection<>(values.subList(0, count));
    }

    public Collection<T> skip(int count) {
        requireNonNegative(count, "count");
        if (count == 0) {
            return this;
        }
        if (count >= values.size()) {
            return empty();
        }
        return new Collection<>(values.subList(count, values.size()));
    }

    public Collection<T> concat(Collection<? extends T> other) {
        Objects.requireNonNull(other, "other");
        return concat(other.values);
    }

    public Collection<T> concat(java.util.Collection<? extends T> other) {
        Objects.requireNonNull(other, "other");
        if (other.isEmpty()) {
            return this;
        }
        List<T> merged = new ArrayList<>(values.size() + other.size());
        merged.addAll(values);
        merged.addAll(other);
        return new Collection<>(merged);
    }

    public Collection<T> sort(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator");
        if (values.size() <= 1) {
            return this;
        }
        List<T> sorted = new ArrayList<>(values);
        sorted.sort(comparator);
        return new Collection<>(sorted);
    }

    public <R extends Comparable<? super R>> Collection<T> sortBy(Function<? super T, ? extends R> keyExtractor) {
        Objects.requireNonNull(keyExtractor, "keyExtractor");
        return sort(Comparator.comparing(keyExtractor, java.util.Comparator.nullsFirst(java.util.Comparator.naturalOrder())));
    }

    public <R> Collection<R> pluck(Function<? super T, ? extends R> mapper) {
        return map(mapper);
    }

    public Collection<Object> pluckPath(String path) {
        Objects.requireNonNull(path, "path");
        if (path.isBlank()) {
            throw new IllegalArgumentException("path must not be blank");
        }
        String[] segments = path.split("\\.");
        List<Object> projected = new ArrayList<>(values.size());
        for (T value : values) {
            projected.add(resolvePath(value, segments));
        }
        return new Collection<>(projected);
    }

    public <K> Map<K, T> toMap(Function<? super T, ? extends K> keySelector) {
        return toMap(keySelector, Function.identity());
    }

    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector) {
        Objects.requireNonNull(keySelector, "keySelector");
        Objects.requireNonNull(valueSelector, "valueSelector");
        Map<K, V> map = new LinkedHashMap<>();
        for (T value : values) {
            map.put(keySelector.apply(value), valueSelector.apply(value));
        }
        return Collections.unmodifiableMap(map);
    }

    public <R> R reduce(R identity, java.util.function.BiFunction<? super R, ? super T, ? extends R> reducer) {
        Objects.requireNonNull(reducer, "reducer");
        R result = identity;
        for (T value : values) {
            result = reducer.apply(result, value);
        }
        return result;
    }

    public Optional<T> reduce(BinaryOperator<T> reducer) {
        Objects.requireNonNull(reducer, "reducer");
        if (values.isEmpty()) {
            return Optional.empty();
        }
        T result = values.get(0);
        for (int index = 1; index < values.size(); index++) {
            result = reducer.apply(result, values.get(index));
        }
        return Optional.ofNullable(result);
    }

    public double sum(ToDoubleFunction<? super T> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        double total = 0.0d;
        for (T value : values) {
            total += mapper.applyAsDouble(value);
        }
        return total;
    }

    public double sum() {
        return sum(value -> requireNumber(value, "sum").doubleValue());
    }

    public OptionalDouble average(ToDoubleFunction<? super T> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        if (values.isEmpty()) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(sum(mapper) / values.size());
    }

    public OptionalDouble average() {
        return average(value -> requireNumber(value, "average").doubleValue());
    }

    public Optional<T> min() {
        return reduce((left, right) -> compareOrThrow(left, right) <= 0 ? left : right);
    }

    public Optional<T> max() {
        return reduce((left, right) -> compareOrThrow(left, right) >= 0 ? left : right);
    }

    public Collection<Object> flatten() {
        if (values.isEmpty()) {
            return Collection.empty();
        }

        List<Object> flattened = new ArrayList<>();
        for (T value : values) {
            flattenValue(value, flattened);
        }
        return new Collection<>(flattened);
    }

    public <K> Collection<Group<K, T>> groupBy(Function<? super T, ? extends K> classifier) {
        Objects.requireNonNull(classifier, "classifier");
        if (values.isEmpty()) {
            return Collection.empty();
        }

        Map<K, List<T>> groups = new LinkedHashMap<>();
        for (T value : values) {
            K key = classifier.apply(value);
            groups.computeIfAbsent(key, ignored -> new ArrayList<>()).add(value);
        }

        List<Group<K, T>> grouped = new ArrayList<>(groups.size());
        for (Map.Entry<K, List<T>> entry : groups.entrySet()) {
            grouped.add(new Group<>(entry.getKey(), new Collection<>(entry.getValue())));
        }
        return new Collection<>(grouped);
    }

    public <K> Collection<Keyed<K, T>> keyBy(Function<? super T, ? extends K> keySelector) {
        Objects.requireNonNull(keySelector, "keySelector");
        if (values.isEmpty()) {
            return Collection.empty();
        }

        List<Keyed<K, T>> keyed = new ArrayList<>(values.size());
        for (T value : values) {
            keyed.add(new Keyed<>(keySelector.apply(value), value));
        }
        return new Collection<>(keyed);
    }

    public Partition<T> partition(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        List<T> matching = new ArrayList<>();
        List<T> rejected = new ArrayList<>();
        for (T value : values) {
            if (predicate.test(value)) {
                matching.add(value);
            } else {
                rejected.add(value);
            }
        }
        return new Partition<>(new Collection<>(matching), new Collection<>(rejected));
    }

    public T firstWhere(Predicate<? super T> predicate) {
        return firstWhere(predicate, null);
    }

    public T firstWhere(Predicate<? super T> predicate, T fallback) {
        Objects.requireNonNull(predicate, "predicate");
        for (T value : values) {
            if (predicate.test(value)) {
                return value;
            }
        }
        return fallback;
    }

    public Collection<T> tap(Consumer<? super Collection<T>> callback) {
        Objects.requireNonNull(callback, "callback");
        callback.accept(this);
        return this;
    }

    public Collection<T> when(boolean condition, UnaryOperator<Collection<T>> callback) {
        Objects.requireNonNull(callback, "callback");
        return condition ? Objects.requireNonNull(callback.apply(this), "callback result") : this;
    }

    public Collection<T> unless(boolean condition, UnaryOperator<Collection<T>> callback) {
        return when(!condition, callback);
    }

    public Collection<Collection<T>> chunk(int size) {
        requirePositive(size, "size");
        if (values.isEmpty()) {
            return Collection.empty();
        }
        List<Collection<T>> chunks = new ArrayList<>();
        for (int index = 0; index < values.size(); index += size) {
            int end = Math.min(index + size, values.size());
            chunks.add(new Collection<>(values.subList(index, end)));
        }
        return new Collection<>(chunks);
    }

    public String join(String delimiter) {
        Objects.requireNonNull(delimiter, "delimiter");
        return values.stream().map(String::valueOf).reduce((left, right) -> left + delimiter + right).orElse("");
    }

    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    private static void requireNonNegative(int value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " must be greater than or equal to zero");
        }
    }

    private static void requirePositive(int value, String name) {
        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than zero");
        }
    }

    private static Number requireNumber(Object value, String operation) {
        if (!(value instanceof Number number)) {
            String actual = value == null ? "null" : value.getClass().getName();
            throw new IllegalStateException("Cannot compute " + operation + " on value of type " + actual);
        }
        return number;
    }

    @SuppressWarnings("unchecked")
    private static <T> int compareOrThrow(T left, T right) {
        if (left == null || right == null) {
            throw new IllegalStateException("Cannot compare null values");
        }
        if (!(left instanceof Comparable<?> comparable)) {
            throw new IllegalStateException("Cannot compare value of type " + left.getClass().getName());
        }
        try {
            return ((Comparable<Object>) comparable).compareTo(right);
        } catch (ClassCastException exception) {
            throw new IllegalStateException("Cannot compare values of different types", exception);
        }
    }

    public record Group<K, T>(K key, Collection<T> values) {
        public Group {
            Objects.requireNonNull(values, "values");
        }
    }

    public record Keyed<K, T>(K key, T value) {
    }

    public record Partition<T>(Collection<T> matching, Collection<T> rejected) {
        public Partition {
            Objects.requireNonNull(matching, "matching");
            Objects.requireNonNull(rejected, "rejected");
        }
    }

    private static void flattenValue(Object value, List<Object> flattened) {
        if (value == null) {
            flattened.add(null);
            return;
        }
        if (value instanceof Collection<?> collection) {
            for (Object item : collection) {
                flattenValue(item, flattened);
            }
            return;
        }
        if (value instanceof java.util.Collection<?> collection) {
            for (Object item : collection) {
                flattenValue(item, flattened);
            }
            return;
        }
        if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            for (int index = 0; index < length; index++) {
                flattenValue(Array.get(value, index), flattened);
            }
            return;
        }
        flattened.add(value);
    }

    private static Object resolvePath(Object value, String[] segments) {
        Object current = value;
        for (String segment : segments) {
            if (current == null) {
                return null;
            }
            current = resolveSegment(current, segment);
        }
        return current;
    }

    private static Object resolveSegment(Object value, String segment) {
        if (value instanceof Map<?, ?> map) {
            return map.get(segment);
        }

        Class<?> type = value.getClass();
        if (type.isRecord()) {
            for (var component : type.getRecordComponents()) {
                if (component.getName().equals(segment)) {
                    try {
                        return component.getAccessor().invoke(value);
                    } catch (ReflectiveOperationException exception) {
                        throw new IllegalStateException("Unable to read record component " + segment, exception);
                    }
                }
            }
        }

        String suffix = Character.toUpperCase(segment.charAt(0)) + segment.substring(1);
        for (String methodName : List.of(segment, "get" + suffix, "is" + suffix)) {
            try {
                Method method = type.getMethod(methodName);
                if (method.getParameterCount() == 0) {
                    return method.invoke(value);
                }
            } catch (ReflectiveOperationException ignored) {
                // Try the next accessor form.
            }
        }
        return null;
    }
}
