package ru.necs.domain.model;

public class Value {

    private String value;
    private String hash;

    public String getValue() {
        return value;
    }

    public Value setValue(final String value) {
        this.value = value;
        return this;
    }

    public String getHash() {
        return hash;
    }

    public Value setHash(final String hash) {
        this.hash = hash;
        return this;
    }

    @Override
    public String toString() {
        return "Value{"
                + " value='" + value + '\''
                + ",hash='" + hash + '\''
                + '}';
    }
}
