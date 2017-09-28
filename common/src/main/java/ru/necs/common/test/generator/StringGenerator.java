package ru.necs.common.test.generator;

import ru.necs.common.test.EntityGenerator;
import ru.necs.common.test.random.MeetupRandom;

public class StringGenerator implements EntityGenerator<String> {

    private static final MeetupRandom RANDOM = new MeetupRandom();

    @Override
    public String generate() {
        return RANDOM.nextUuidString();
    }
}
