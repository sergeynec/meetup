package ru.necs.functional.test;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.necs.client.model.Key.key;
import java.util.Optional;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.necs.client.ConfigClient;
import ru.necs.client.common.converter.StringConverter;
import ru.necs.client.model.Key;
import ru.necs.client.rest.RestConfigClientFactoryBean;
import ru.necs.client.rest.model.RestConfigContext;
import ru.necs.functional.AppServerConfig;

@Test
@Configuration
@ContextConfiguration(classes = {AppServerConfig.class, RestClientTestFT.class})
public class RestClientTestFT extends AbstractTestNGSpringContextTests {

    private static final Key KEY = key(
            "lalala"
    );

    @Autowired
    private ConfigClient client;

    @Test
    public void test() throws Exception {
        final Optional<String> actual = client.get(KEY, String.class);

        assertThat(actual.isPresent()).isTrue();
    }

    @Bean
    public FactoryBean<ConfigClient> restClient() {
        return new RestConfigClientFactoryBean(
                singletonMap(String.class, new StringConverter()),
                new RestConfigContext().setUrl("http://localhost:8008/functional-test/phrase")
        );
    }
}
