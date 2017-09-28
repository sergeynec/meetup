package ru.necs.client;

import java.util.Optional;
import ru.necs.client.model.Key;

public interface ConfigClient {

    <T> Optional<T> get(Key key, Class<T> clazz);
}
