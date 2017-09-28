package ru.necs.web.model;

import java.util.List;

public class KeyJs {

    private String name;
    private List<AttributeJs> attributes;

    public String getName() {
        return name;
    }

    public KeyJs setName(final String name) {
        this.name = name;
        return this;
    }

    public List<AttributeJs> getAttributes() {
        return attributes;
    }

    public KeyJs setAttributes(final List<AttributeJs> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public String toString() {
        return "KeyJs{"
                + " name='" + name + '\''
                + ",attributes=" + attributes
                + '}';
    }
}
