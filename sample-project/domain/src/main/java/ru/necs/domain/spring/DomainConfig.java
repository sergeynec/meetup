package ru.necs.domain.spring;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import java.util.List;
import java.util.function.Function;
import javax.annotation.PostConstruct;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import ru.necs.common.orika.MapperRegister;
import ru.necs.da.repository.ValueRepository;
import ru.necs.da.spring.DaConfig;
import ru.necs.domain.model.Key;

@Configuration
@Import(DaConfig.class)
@ComponentScan({"ru.necs.domain.service", "ru.necs.domain.util", "ru.necs.domain.orika"})
public class DomainConfig {

    @Autowired
    private InitialDbLoader initialDbLoader;

    @Autowired
    private List<MapperRegister> registers;

    @Bean
    @Autowired
    @Profile("h2")
    public InitialDbLoader initialDbLoader(final ValueRepository repository, final Function<Key, String> keyToHashConverter) {
        return new InitialDbLoader(repository, keyToHashConverter);
    }

    @Bean
    public MapperFactory orikaMapper() {
        final DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .useAutoMapping(false)
                .build();

        ofNullable(registers).orElse(emptyList()).forEach(mapperRegister -> mapperRegister.register(mapperFactory));

        return mapperFactory;
    }

    @PostConstruct
    public void init() {
        initialDbLoader.load(
                asList(
                        new Key().setName("lalala"),
                        new Key().setName("bububub"),
                        new Key().setName("foo"),
                        new Key().setName("bar")
                )
        );
    }
}
