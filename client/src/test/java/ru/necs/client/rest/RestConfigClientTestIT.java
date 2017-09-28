package ru.necs.client.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import static ru.necs.client.model.Key.key;
import java.util.Optional;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.necs.client.ConfigClient;
import ru.necs.client.model.Key;
import ru.necs.common.test.EntityGenerators;
import ru.necs.common.test.generator.StringGenerator;

@Test
@ContextConfiguration("classpath:/restConfigClientTestIT.xml")
public class RestConfigClientTestIT extends AbstractTestNGSpringContextTests {

    public static final int PORT = 9999;

    @Autowired
    private ConfigClient client;

    private ClientAndServer server;

    @BeforeClass
    public void beforeClass() throws Exception {
        this.server = startClientAndServer(PORT);
    }

    @BeforeMethod
    public void before() throws Exception {
        server.reset();
    }

    @Test
    @EntityGenerators({StringGenerator.class, StringGenerator.class})
    public void readValue(final String name, final String value) throws Exception {
        final Key key = key(name);

        server.when(
                request()
                        .withBody(json(
                                key
                        )),
                exactly(1)
        ).respond(
                response()
                        .withStatusCode(200)
                        .withBody(value)
        );

        final Optional<String> actual = client.get(key, String.class);

        assertThat(actual.orElseThrow(RuntimeException::new)).isEqualTo(value);
    }

    @AfterClass
    public void tearDown() throws Exception {
        server.stop(true);
    }
}
