package ru.necs.domain.util;

import static org.apache.commons.lang3.Validate.notNull;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import ru.necs.domain.model.Key;

@Component
public class KeyToHashConverter implements Function<Key, String> {

    @Override
    public String apply(final Key key) {
        notNull(key.getName(), "Key's name can't be null");

        if (key.getAttributes() == null || key.getAttributes().isEmpty()) {
            return key.getName();
        }

        return key.getName() + "#" + attributesHash(key);
    }

    private String attributesHash(final Key key) {
        return key.getAttributes()
                  .stream()
                  .map(attribute -> attribute.getKey() + "|" + attribute.getValue())
                  .reduce(String::concat)
                  .orElse("");
    }
}
