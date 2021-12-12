package com.example.balance.service.util;

import com.example.balance.service.model.Extra;
import com.example.balance.service.model.Response;
import com.example.balance.service.model.ResponseCode;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResponseComposer {

    private ResponseComposer() {
    }

    public static Response compose(ResponseCode code) {
        return new Response(code, null);
    }

    public static Response compose(ResponseCode code, String message) {
        boolean messageProvided = message != null && !message.isEmpty();
        List<Extra> extras = messageProvided ? List.of(new Extra("message", message)) : null;
        return new Response(code, extras);
    }

    public static Response compose(ResponseCode code, Map<String, String> extrasMap) {
        Objects.requireNonNull(extrasMap, "extras map should be not null");
        List<Extra> extras = extrasMap.entrySet().stream()
                .map(e -> new Extra(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        return new Response(code, extras);
    }
}
