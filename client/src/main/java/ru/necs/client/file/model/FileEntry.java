package ru.necs.client.file.model;

import ru.necs.client.model.Key;
import ru.necs.client.model.Value;

public class FileEntry {

    private Key key;
    private Value value;

    public Key getKey() {
        return key;
    }

    public FileEntry setKey(final Key key) {
        this.key = key;
        return this;
    }

    public Value getValue() {
        return value;
    }

    public FileEntry setValue(final Value value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "FileEntry{"
                + " key=" + key
                + ",value=" + value
                + '}';
    }
}
