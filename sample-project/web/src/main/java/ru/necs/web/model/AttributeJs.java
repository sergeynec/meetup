package ru.necs.web.model;

public class AttributeJs {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public AttributeJs setKey(final String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AttributeJs setValue(final String value) {
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
