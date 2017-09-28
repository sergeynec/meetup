package ru.necs.domain.orika;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;
import ru.necs.common.orika.MapperRegister;
import ru.necs.da.model.ValueEntity;
import ru.necs.domain.model.Value;

@Component
public class DomainMapperRegister implements MapperRegister {

    @Override
    public void register(final MapperFactory mapperFactory) {
        mapperFactory.classMap(ValueEntity.class, Value.class)
                     .exclude("id")
                     .byDefault()
                     .register();
    }
}
