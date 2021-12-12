package com.example.balance.service.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import java.util.Objects;

public class Extra {
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlText
    private String value;

    public Extra(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Extra() {
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Extra{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Extra extra = (Extra) o;
        return name.equals(extra.name) && value.equals(extra.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
