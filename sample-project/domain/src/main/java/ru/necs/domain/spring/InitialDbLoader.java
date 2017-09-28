package ru.necs.domain.spring;

import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import java.util.Random;
import java.util.function.Function;
import org.springframework.transaction.annotation.Transactional;
import ru.necs.da.model.ValueEntity;
import ru.necs.da.repository.ValueRepository;
import ru.necs.domain.model.Key;

public class InitialDbLoader {

    private static final Random RANDOM = new Random();

    private final ValueRepository repository;
    private final Function<Key, String> keyToHashConverter;

    public InitialDbLoader(final ValueRepository repository, final Function<Key, String> keyToHashConverter) {
        this.repository = repository;
        this.keyToHashConverter = keyToHashConverter;
    }

    @Transactional
    public void load(final Iterable<Key> keys) {
        repository.save(
                stream(keys.spliterator(), false)
                        .map(key -> new ValueEntity().setValue(valueOf(RANDOM.nextInt())).setHash(keyToHashConverter.apply(key)))
                        .collect(toList())
        );
    }
}
