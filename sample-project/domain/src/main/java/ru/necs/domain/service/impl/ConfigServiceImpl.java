package ru.necs.domain.service.impl;

import static org.apache.commons.lang3.Validate.notNull;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.EntityNotFoundException;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.necs.da.model.ValueEntity;
import ru.necs.da.repository.ValueRepository;
import ru.necs.domain.model.Key;
import ru.necs.domain.model.Value;
import ru.necs.domain.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

    private final ValueRepository repository;
    private final Function<Key, String> keyToHashConverter;
    private final BoundMapperFacade<ValueEntity, Value> responseMapper;

    @Autowired
    public ConfigServiceImpl(final ValueRepository repository,
                             final Function<Key, String> keyToHashConverter,
                             final MapperFactory mapperFactory) {
        this.repository = notNull(repository);
        this.keyToHashConverter = notNull(keyToHashConverter);
        this.responseMapper = notNull(mapperFactory.getMapperFacade(ValueEntity.class, Value.class));
    }

    @Override
    public Value lookup(final Key key) {
        final Optional<ValueEntity> optional = repository.findByHash(
                keyToHashConverter.apply(key)
        );

        return optional.map(responseMapper::map).orElseThrow(EntityNotFoundException::new);
    }
}
