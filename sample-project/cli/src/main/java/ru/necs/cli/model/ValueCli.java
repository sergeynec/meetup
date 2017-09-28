package ru.necs.cli.model;

public class ValueCli {

    private String value;
    private String hash;

    public String getValue() {
        return value;
    }

    public ValueCli setValue(final String value) {
        this.value = value;
        return this;
    }

    public String getHash() {
        return hash;
    }

    public ValueCli setHash(final String hash) {
        this.hash = hash;
        return this;
    }

    @Override
    public String toString() {
        return "ValueCli{"
                + " value='" + value + '\''
                + ",hash='" + hash + '\''
                + '}';
    }
}
