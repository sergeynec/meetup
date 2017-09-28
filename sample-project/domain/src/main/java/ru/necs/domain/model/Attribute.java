package ru.necs.domain.model;

public class Attribute {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public Attribute setKey(final String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Attribute setValue(final String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Attribute{"
                + " key='" + key + '\''
                + ",value='" + value + '\''
                + '}';
    }
}
