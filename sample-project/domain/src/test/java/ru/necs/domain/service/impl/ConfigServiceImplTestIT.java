package ru.necs.domain.service.impl;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.function.Function;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.necs.common.test.EntityGenerators;
import ru.necs.common.test.generator.StringGenerator;
import ru.necs.da.model.ValueEntity;
import ru.necs.da.repository.ValueRepository;
import ru.necs.domain.model.Attribute;
import ru.necs.domain.model.Key;
import ru.necs.domain.model.Value;
import ru.necs.domain.service.ConfigService;
import ru.necs.domain.spring.DomainConfig;

@Test
@ActiveProfiles("h2")
@ContextConfiguration(classes = DomainConfig.class)
public class ConfigServiceImplTestIT extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ConfigService service;

    @Autowired
    private ValueRepository repository;

    @Autowired
    private Function<Key, String> keyToHashConverter;

    @Test
    public void test() throws Exception {
        final String value = "100500";
        final Key key = new Key()
                .setName("key")
                .setAttributes(
                        asList(
                                new Attribute().setKey("attr1").setValue("value1"),
                                new Attribute().setKey("attr2").setValue("value2")
                        )
                );
        final ValueEntity dbEntity = repository.save(
                new ValueEntity()
                        .setValue(value)
                        .setHash(keyToHashConverter.apply(key))
        );

        final Value actual = service.lookup(key);

        assertThat(actual.getValue()).isEqualTo(value);
        assertThat(actual.getHash()).isEqualTo(dbEntity.getHash());
    }

    @EntityGenerators(StringGenerator.class)
    @Test(expectedExceptions = EntityNotFoundException.class)
    public void throwIfValueIsNotFound(final String unknownParameterName) throws Exception {
        service.lookup(
                new Key().setName(unknownParameterName)
        );
    }
}
