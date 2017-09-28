package ru.necs.domain.model;

import java.util.List;

public class Key {

    private String name;
    private List<Attribute> attributes;

    public String getName() {
        return name;
    }

    public Key setName(final String name) {
        this.name = name;
        return this;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Key setAttributes(final List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public String toString() {
        return "Key{"
                + " name='" + name + '\''
                + ",attributes=" + attributes
                + '}';
    }
}
