package ru.necs.cli.orika;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;
import ru.necs.cli.model.KeyCli;
import ru.necs.cli.model.ValueCli;
import ru.necs.common.orika.MapperRegister;
import ru.necs.domain.model.Key;
import ru.necs.domain.model.Value;

@Component
public class CliMapperRegister implements MapperRegister {

    @Override
    public void register(final MapperFactory mapperFactory) {
        mapperFactory.classMap(KeyCli.class, Key.class)
                     .byDefault()
                     .register();
        mapperFactory.classMap(Value.class, ValueCli.class)
                     .byDefault()
                     .register();
    }
}
