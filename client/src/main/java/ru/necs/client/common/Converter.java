package ru.necs.client.common;

public interface Converter<S, T> {

    T convert(S source);
}
