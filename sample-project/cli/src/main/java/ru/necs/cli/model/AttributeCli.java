package ru.necs.cli.model;

public class AttributeCli {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public AttributeCli setKey(final String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AttributeCli setValue(final String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "AttributeCli{"
                + " key='" + key + '\''
                + ",value='" + value + '\''
                + '}';
    }
}
