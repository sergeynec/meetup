package ru.necs.client.common.converter;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.Test;
import ru.necs.common.test.EntityGenerators;
import ru.necs.common.test.generator.NullableStringGenerator;

@Test
public class StringConverterTest {

    @Test
    @EntityGenerators(NullableStringGenerator.class)
    public void sourceEqualsTarget(final String source) throws Exception {
        final String actual = new StringConverter().convert(source);

        assertThat(actual).isEqualTo(source);
    }
}
