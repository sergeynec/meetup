package ru.necs.cli.service;

import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.necs.cli.model.KeyCli;
import ru.necs.cli.model.ValueCli;
import ru.necs.domain.model.Key;
import ru.necs.domain.model.Value;
import ru.necs.domain.service.ConfigService;

@Component
public class KeyController {

    private static final Logger LOGGER = getLogger(KeyController.class);

    private final ConfigService configService;
    private final BoundMapperFacade<KeyCli, Key> requestMapper;
    private final BoundMapperFacade<Value, ValueCli> responseMapper;

    @Autowired
    public KeyController(final ConfigService configService, final MapperFactory mapperFactory) {
        this.configService = notNull(configService);
        this.requestMapper = notNull(mapperFactory.getMapperFacade(KeyCli.class, Key.class));
        this.responseMapper = notNull(mapperFactory.getMapperFacade(Value.class, ValueCli.class));
    }

    public ValueCli lookup(final KeyCli requestCli) {
        LOGGER.info("Receive message: {}", requestCli);

        return responseMapper.map(
                configService.lookup(
                        requestMapper.map(requestCli)
                )
        );
    }
}
