package ru.necs.common.test.random;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class MeetupRandom extends Random {

    private static final int DEFAULT_LIST_MAX_SIZE = 10;

    public String nextUuidString() {
        return randomUUID().toString();
    }

    public String nextNullableUuidString() {
        return nextBoolean() ? null : nextUuidString();
    }

    public Long nextNullableLong() {
        return nextBoolean() ? null : nextLong();
    }

    public int nextPositiveInteger(final int max) {
        return nextInt(max) + 1;
    }

    public <T> List<T> nextList(final Supplier<T> supplier) {
        return nextList(supplier, DEFAULT_LIST_MAX_SIZE);
    }

    public <T> List<T> nextList(final Supplier<T> supplier, final int maxSize) {
        return generate(supplier)
                .limit(nextPositiveInteger(maxSize))
                .collect(toList());
    }
}
