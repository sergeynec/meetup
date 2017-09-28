package ru.necs.client.file;

import static java.util.Optional.ofNullable;
import java.util.Map;
import java.util.Optional;
import ru.necs.client.common.AbstractConfigClient;
import ru.necs.client.common.Converter;
import ru.necs.client.model.Key;
import ru.necs.client.model.Value;

public class FileConfigClient extends AbstractConfigClient {

    private final Map<Key, Value> dictionary;

    public FileConfigClient(final Map<Class<?>, Converter<String, ?>> convertersRegistry, final Map<Key, Value> dictionary) {
        super(convertersRegistry);

        this.dictionary = dictionary;
    }

    @Override
    protected Optional<String> getStringValue(final Key key) {
        return ofNullable(dictionary.get(key))
                .map(Value::getValue);
    }
}
