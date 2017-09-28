package ru.necs.client.file;

import static java.lang.String.format;
import static java.nio.charset.Charset.forName;
import static java.util.stream.Collectors.toMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import ru.necs.client.file.model.FileEntry;
import ru.necs.client.model.Key;
import ru.necs.client.model.Value;

public class FileReader {

    private static final TypeReference<List<FileEntry>> CONTENT_TYPE = new TypeReference<List<FileEntry>>() {
    };

    private final ObjectMapper objectMapper;

    public FileReader(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<Key, Value> read(final Resource file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(format("File [%s] is not found", file.getFilename()));
        }

        try (final InputStream stream = file.getInputStream()) {
            return objectMapper.<List<FileEntry>>readValue(IOUtils.toString(stream, forName("UTF-8")), CONTENT_TYPE)
                    .stream()
                    .collect(
                            toMap(FileEntry::getKey, FileEntry::getValue)
                    );
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
