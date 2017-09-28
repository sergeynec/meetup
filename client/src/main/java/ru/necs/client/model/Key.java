package ru.necs.client.model;

import static java.util.Arrays.asList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class Key {

    private final String name;
    private final Iterable<Attribute> attributes;

    private Key(final String name, final Attribute... attributes) {
        this.name = name;
        this.attributes = asList(attributes);
    }

    public String getName() {
        return name;
    }

    public Iterable<Attribute> getAttributes() {
        return attributes;
    }

    @JsonCreator
    public static Key key(@JsonProperty("name") final String name,
                          @JsonProperty("attributes") final Attribute... attributes) {
        return new Key(name, attributes);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Key key = (Key) o;
        return Objects.equals(name, key.name) &&
                Objects.equals(attributes, key.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, attributes);
    }

    @Override
    public String toString() {
        return "Key{"
                + " name='" + name + '\''
                + ",attributes=" + attributes
                + '}';
    }

    public static class Attribute {

        private final String key;
        private final String value;

        private Attribute(final String key, final String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @JsonCreator
        public static Attribute attribute(
                @JsonProperty("key") final String key,
                @JsonProperty("value") final String value) {
            return new Attribute(key, value);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            final Attribute attribute = (Attribute) o;
            return Objects.equals(key, attribute.key) &&
                    Objects.equals(value, attribute.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return "Attribute{"
                    + " key='" + key + '\''
                    + ",value='" + value + '\''
                    + '}';
        }
    }
}
