package ru.necs.domain.generator;

import ru.necs.common.test.EntityGenerator;
import ru.necs.common.test.random.MeetupRandom;
import ru.necs.da.model.ValueEntity;

public class ValueEntityGenerator implements EntityGenerator<ValueEntity> {

    private static final MeetupRandom RANDOM = new MeetupRandom();

    @Override
    public ValueEntity generate() {
        return new ValueEntity()
                .setId(RANDOM.nextNullableLong())
                .setHash(RANDOM.nextNullableUuidString())
                .setValue(RANDOM.nextNullableUuidString());
    }
}
