package ru.necs.client.common.converter;

import ru.necs.client.common.Converter;

public class StringConverter implements Converter<String, String> {

    @Override
    public String convert(final String source) {
        return source;
    }
}
