package ru.necs.domain.generator;

import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import ru.necs.common.test.EntityGenerator;
import ru.necs.common.test.random.MeetupRandom;
import ru.necs.domain.model.Attribute;
import ru.necs.domain.model.Key;

public class KeyGenerator implements EntityGenerator<Key> {

    private static final MeetupRandom RANDOM = new MeetupRandom();

    @Override
    public Key generate() {
        return new Key()
                .setName(RANDOM.nextUuidString())
                .setAttributes(
                        Stream.generate(Attribute::new)
                              .limit(RANDOM.nextInt(10) + 1)
                              .map(
                                      attribute -> attribute
                                              .setKey(RANDOM.nextUuidString())
                                              .setValue(RANDOM.nextUuidString())
                              )
                              .collect(toList())
                );
    }
}
