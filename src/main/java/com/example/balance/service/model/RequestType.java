package com.example.balance.service.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum RequestType {
    CREATE_AGT("CREATE-AGT"),
    GET_BALANCE("GET-BALANCE");

    @JsonValue
    private final String label;

    RequestType(String label) {
        this.label = label;
    }

    public static RequestType of(String label) {
        return Arrays.stream(RequestType.values())
                .filter(rt -> rt.label.equals(label))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unknown request type"));
    }

    @Override
    public String toString() {
        return label;
    }
}
