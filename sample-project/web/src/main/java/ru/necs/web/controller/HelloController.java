package ru.necs.web.controller;

import static org.apache.commons.lang3.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.necs.common.spring.web.JsonController;
import ru.necs.domain.model.Key;
import ru.necs.domain.model.Value;
import ru.necs.domain.service.ConfigService;
import ru.necs.web.model.KeyJs;
import ru.necs.web.model.ValueJs;

@JsonController
public class HelloController {

    private static final Logger LOGGER = getLogger(HelloController.class);

    private final ConfigService service;
    private final BoundMapperFacade<KeyJs, Key> requestMapper;
    private final BoundMapperFacade<Value, ValueJs> responseMapper;

    @Autowired
    public HelloController(final ConfigService service, final MapperFactory mapperFactory) {
        this.service = notNull(service);
        this.requestMapper = notNull(mapperFactory.getMapperFacade(KeyJs.class, Key.class));
        this.responseMapper = notNull(mapperFactory.getMapperFacade(Value.class, ValueJs.class));
    }

    @PostMapping("phrase")
    public ValueJs say(@RequestBody final KeyJs requestJs) {
        LOGGER.info("Receive message: {}", requestJs);

        return responseMapper.map(
                service.lookup(
                        requestMapper.map(requestJs)
                )
        );
    }
}
