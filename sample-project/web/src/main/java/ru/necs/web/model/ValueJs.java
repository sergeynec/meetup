package ru.necs.web.model;

public class ValueJs {

    private String value;
    private String hash;

    public String getValue() {
        return value;
    }

    public ValueJs setValue(final String value) {
        this.value = value;
        return this;
    }

    public String getHash() {
        return hash;
    }

    public ValueJs setHash(final String hash) {
        this.hash = hash;
        return this;
    }

    @Override
    public String toString() {
        return "ValueJs{"
                + " value='" + value + '\''
                + ",hash='" + hash + '\''
                + '}';
    }
}
