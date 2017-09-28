package ru.necs.domain.service;

import ru.necs.domain.model.Key;
import ru.necs.domain.model.Value;

public interface ConfigService {

    Value lookup(Key key);
}
