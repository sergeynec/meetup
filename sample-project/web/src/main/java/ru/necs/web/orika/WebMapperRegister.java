package ru.necs.web.orika;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;
import ru.necs.common.orika.MapperRegister;
import ru.necs.domain.model.Key;
import ru.necs.domain.model.Value;
import ru.necs.web.model.KeyJs;
import ru.necs.web.model.ValueJs;

@Component
public class WebMapperRegister implements MapperRegister {

    @Override
    public void register(final MapperFactory mapperFactory) {
        mapperFactory.classMap(KeyJs.class, Key.class)
                     .byDefault()
                     .register();
        mapperFactory.classMap(Value.class, ValueJs.class)
                     .byDefault()
                     .register();
    }
}
