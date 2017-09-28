package ru.necs.domain.util;

import static java.util.stream.Collectors.joining;
import static java.util.stream.StreamSupport.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.necs.common.test.assertj.Assertions.failIfSuccess;
import java.util.function.Function;
import org.testng.annotations.Test;
import ru.necs.common.test.EntityGenerators;
import ru.necs.common.test.generator.StringGenerator;
import ru.necs.domain.generator.KeyGenerator;
import ru.necs.domain.model.Key;

@Test
public class KeyToHashConverterTest {

    private final Function<Key, String> converter = new KeyToHashConverter();

    @Test
    public void throwIfKeyNameIsNull() throws Exception {
        try {
            converter.apply(new Key());
        } catch (final NullPointerException e) {
            assertThat(e.getMessage()).isEqualTo("Key's name can't be null");

            return;
        }

        failIfSuccess();
    }

    @Test
    @EntityGenerators(StringGenerator.class)
    public void returnNameIfKeyAttributesIsNull(final String name) throws Exception {
        final String actual = converter.apply(new Key().setName(name));

        assertThat(actual).isEqualTo(name);
    }

    @Test
    @EntityGenerators(KeyGenerator.class)
    public void returnNamePlusJoinedAttributesOnVerticalBar(final Key key) throws Exception {
        final String actual = converter.apply(key);

        assertThat(actual).isEqualTo(
                key.getName() + "#" + stream(key.getAttributes().spliterator(), false)
                        .map(attribute -> attribute.getKey() + "|" + attribute.getValue())
                        .collect(joining())
        );
    }
}
