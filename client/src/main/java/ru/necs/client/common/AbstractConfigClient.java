package ru.necs.client.common;

import static java.lang.String.format;
import java.util.Map;
import java.util.Optional;
import ru.necs.client.ConfigClient;
import ru.necs.client.model.Key;

public abstract class AbstractConfigClient implements ConfigClient {

    private final Map<Class<?>, Converter<String, ?>> convertersRegistry;

    public AbstractConfigClient(final Map<Class<?>, Converter<String, ?>> convertersRegistry) {
        this.convertersRegistry = convertersRegistry;
    }

    @Override
    public <T> Optional<T> get(final Key key, final Class<T> clazz) {
        if (!convertersRegistry.containsKey(clazz)) {
            throw new IllegalArgumentException(format("There is no registered value converter for class [%s]", clazz.getName()));
        }

        return getStringValue(key).map(((Converter<String, T>) convertersRegistry.get(clazz))::convert);
    }

    protected abstract Optional<String> getStringValue(final Key key);
}
