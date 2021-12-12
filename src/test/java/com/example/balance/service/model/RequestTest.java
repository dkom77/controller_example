package com.example.balance.service.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestTest {

    @Test
    void serializationTest() throws JsonProcessingException {
        Request request = createRequest();

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(request);

        assertEquals(modelXml(), xml);
    }

    @Test
    void deserializationTest() throws JsonProcessingException {
        String xml = modelXml();
        XmlMapper xmlMapper = new XmlMapper();
        Request request = xmlMapper.readValue(xml, Request.class);

        Request modelRequest = createRequest();
        assertEquals(modelRequest, request);
    }

    private Request createRequest() {
        List<Extra> extras = List.of(
                new Extra("login", "login_1"),
                new Extra("password", "my_password")
        );

        RequestType requestType = RequestType.of("CREATE-AGT");
        return new Request(requestType, extras);
    }

    private String modelXml() {
        return "<request><request-type>CREATE-AGT</request-type><extra name=\"login\">login_1</extra><extra name=\"password\">my_password</extra></request>";
    }

}
