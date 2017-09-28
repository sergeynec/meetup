package ru.necs.client.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.data.MapEntry.entry;
import static ru.necs.client.model.Key.key;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;
import ru.necs.client.model.Key;
import ru.necs.client.model.Value;

@Test
public class FileReaderTestIT {

    @Test
    public void read() throws Exception {
        final Map<Key, Value> actual = new FileReader(new ObjectMapper()).read(
                new ClassPathResource("/fileReaderTestIT.json")
        );

        assertThat(actual).containsOnly(
                entry(key("key2"), new Value("100500")),
                entry(key("key1"), new Value("value1")),
                entry(key("key3"), new Value("true"))
        );
    }

    @Test
    public void throwIfFileIsNotFound() throws Exception {
        try {
            new FileReader(new ObjectMapper()).read(
                    new ClassPathResource("/fileReaderTestIT_not_existed.json")
            );
        } catch (final IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("File [fileReaderTestIT_not_existed.json] is not found");

            return;
        }

        fail("Unexpected success!!!");
    }

    @Test
    public void throwIfKeyDuplicationInFile() throws Exception {
        try {
            new FileReader(new ObjectMapper()).read(
                    new ClassPathResource("/fileReaderTestIT_duplication.json")
            );
        } catch (final IllegalStateException e) {
            assertThat(e.getMessage()).startsWith("Duplicate key ");

            return;
        }

        fail("Unexpected success!!!");
    }
}
