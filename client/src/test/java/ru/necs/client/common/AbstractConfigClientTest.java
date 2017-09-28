package ru.necs.client.common;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.necs.client.model.Key.key;
import static ru.necs.common.test.assertj.Assertions.failIfSuccess;
import java.util.Optional;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.necs.client.model.Key;
import ru.necs.common.test.EntityGenerators;
import ru.necs.common.test.generator.StringGenerator;

@Test
public class AbstractConfigClientTest {

    @Mock
    private Converter<String, String> converter;

    @BeforeMethod
    public void before() throws Exception {
        initMocks(this);
    }

    @Test
    public void throwIfThereIsNoAppropriateConverter() throws Exception {
        try {
            new AbstractConfigClient(emptyMap()) {
                @Override
                protected Optional<String> getStringValue(final Key key) {
                    return empty();
                }
            }.get(key("key1"), String.class);
        } catch (final IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("There is no registered value converter for class [java.lang.String]");

            return;
        }

        failIfSuccess();
    }

    @Test
    public void returnAbsentIfGetStringValueReturnEmptyValue() throws Exception {
        final Optional<String> actual = new AbstractConfigClient(singletonMap(String.class, converter)) {
            @Override
            protected Optional<String> getStringValue(final Key key) {
                return empty();
            }
        }.get(key("key1"), String.class);

        assertThat(actual.isPresent()).isFalse();
    }

    @Test
    @EntityGenerators({StringGenerator.class, StringGenerator.class})
    public void callConverterIfGetStringValueReturnValue(final String value, final String expected) throws Exception {
        when(converter.convert(value)).thenReturn(expected);

        final Optional<String> actual = new AbstractConfigClient(singletonMap(String.class, converter)) {
            @Override
            protected Optional<String> getStringValue(final Key key) {
                return of(value);
            }
        }.get(key("key1"), String.class);

        assertThat(actual.orElseThrow(RuntimeException::new)).isEqualTo(expected);

        verify(converter).convert(value);
        verifyNoMoreInteractions(converter);
    }
}
