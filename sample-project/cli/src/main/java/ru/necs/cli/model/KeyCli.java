package ru.necs.cli.model;

import java.util.List;

public class KeyCli {

    private String name;
    private List<AttributeCli> attributes;

    public String getName() {
        return name;
    }

    public KeyCli setName(final String name) {
        this.name = name;
        return this;
    }

    public List<AttributeCli> getAttributes() {
        return attributes;
    }

    public KeyCli setAttributes(final List<AttributeCli> attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public String toString() {
        return "KeyCli{"
                + " name='" + name + '\''
                + ",attributes=" + attributes
                + '}';
    }
}
