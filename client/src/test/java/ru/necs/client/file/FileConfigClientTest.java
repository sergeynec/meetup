package ru.necs.client.file;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.necs.client.model.Key.key;
import java.util.Optional;
import org.testng.annotations.Test;
import ru.necs.client.common.converter.StringConverter;
import ru.necs.client.model.Value;
import ru.necs.common.test.EntityGenerators;
import ru.necs.common.test.generator.StringGenerator;

@Test
public class FileConfigClientTest {

    @Test
    public void returnAbsentIfNoEntry() throws Exception {
        final Optional<String> actual = new FileConfigClient(
                singletonMap(String.class, new StringConverter()),
                emptyMap()
        ).getStringValue(key("key1"));

        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @EntityGenerators({StringGenerator.class, StringGenerator.class})
    public void returnValue(final String key, final String value) throws Exception {
        final Optional<String> actual = new FileConfigClient(
                singletonMap(String.class, new StringConverter()),
                singletonMap(key(key), new Value(value))
        ).getStringValue(key(key));

        assertThat(actual.orElseThrow(RuntimeException::new)).isEqualTo(value);
    }
}
