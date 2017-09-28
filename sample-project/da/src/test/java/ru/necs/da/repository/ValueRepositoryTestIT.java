package ru.necs.da.repository;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.necs.common.test.EntityGenerator;
import ru.necs.common.test.EntityGenerators;
import ru.necs.common.test.generator.StringGenerator;
import ru.necs.common.test.random.MeetupRandom;
import ru.necs.da.model.ValueEntity;
import ru.necs.da.spring.DaConfig;

@Test
@ActiveProfiles("h2")
@ContextConfiguration(classes = DaConfig.class)
public class ValueRepositoryTestIT extends AbstractTransactionalTestNGSpringContextTests {

    private static final MeetupRandom RANDOM = new MeetupRandom();

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ValueRepository repository;

    @Test
    @EntityGenerators(ListValueEntityGenerator.class)
    public void returnExistedValueEntity(final List<ValueEntity> entities) throws Exception {
        final ValueEntity firstValue = entities.get(0);

        repository.save(entities);
        entityManager.flush();

        final Optional<ValueEntity> actual = repository.findByHash(
                firstValue.getHash()
        );

        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getValue()).isEqualTo(firstValue.getValue());
        assertThat(actual.get().getHash()).isEqualTo(firstValue.getHash());
    }

    @Test
    @EntityGenerators({ListValueEntityGenerator.class, StringGenerator.class})
    public void returnEmptyIfNotFound(final List<ValueEntity> entities, final String unknownHash) throws Exception {
        repository.save(entities);

        final Optional<ValueEntity> actual = repository.findByHash(unknownHash);

        assertThat(actual.isPresent()).isFalse();
    }

    private static class ListValueEntityGenerator implements EntityGenerator<List<ValueEntity>> {

        @Override
        public List<ValueEntity> generate() {
            return Stream.generate(ValueEntity::new)
                         .limit(10)
                         .map(entity -> entity.setValue(RANDOM.nextUuidString()).setHash(RANDOM.nextUuidString()))
                         .collect(toList());
        }
    }
}
