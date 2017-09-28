package ru.necs.client.file;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.necs.client.model.Key.Attribute.attribute;
import static ru.necs.client.model.Key.key;
import static ru.necs.common.test.assertj.Assertions.failIfSuccess;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import ru.necs.client.ConfigClient;
import ru.necs.client.model.Key;

@Test
@ContextConfiguration("classpath:/fileConfigClientXmlTestIT.xml")
public class FileConfigClientXmlTestIT extends AbstractTestNGSpringContextTests {

    private static final Key KEY = key(
            "string.parameter",
            attribute("OS", "Windows"),
            attribute("version", "7")
    );

    @Autowired
    private ConfigClient client;

    @Test
    public void lookupExistedValue() throws Exception {
        final Optional<String> actual = client.get(KEY, String.class);

        assertThat(actual.orElseThrow(RuntimeException::new)).isEqualTo("string.value");
    }

    @Test
    public void throwIfThereIsNoAppropriateValueConverter() throws Exception {
        try {
            client.get(KEY, Long.class);
        } catch (final IllegalArgumentException e) {
            return;
        }

        failIfSuccess();
    }
}
