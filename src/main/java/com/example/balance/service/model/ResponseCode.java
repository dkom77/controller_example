package com.example.balance.service.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ResponseCode {
    OK(0),
    USER_EXIST(1),
    TECHNICAL_ERROR(2),
    USER_NOT_EXIST(3),
    WRONG_PASSWORD(4);

    @JsonValue
    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public static ResponseCode of(int code) {
        return Arrays.stream(ResponseCode.values())
                .filter(rc -> rc.code == code)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Unknown response code"));
    }

    public int getCode() {
        return code;
    }
}
