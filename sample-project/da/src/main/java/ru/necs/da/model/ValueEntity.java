package ru.necs.da.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parameter_value")
public class ValueEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "hash")
    private String hash;

    public Long getId() {
        return id;
    }

    public ValueEntity setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ValueEntity setValue(final String value) {
        this.value = value;
        return this;
    }

    public String getHash() {
        return hash;
    }

    public ValueEntity setHash(final String hash) {
        this.hash = hash;
        return this;
    }
}
