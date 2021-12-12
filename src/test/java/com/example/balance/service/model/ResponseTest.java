package com.example.balance.service.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    @Test
    void serializationTest() throws JsonProcessingException {
        Response response = createResponse();

        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(response);
        System.out.println(xml);

        assertEquals(modelXml(), xml);
    }

    @Test
    void deserializationTest() throws JsonProcessingException {
        String xml = modelXml();
        XmlMapper xmlMapper = new XmlMapper();
        Response response = xmlMapper.readValue(xml, Response.class);

        Response modelResponse = createResponse();
        assertEquals(modelResponse, response);
    }

    private Response createResponse() {
        List<Extra> extras = List.of(
                new Extra("balance", "100.00")
        );

        ResponseCode code = ResponseCode.of(0);
        return new Response(code, extras);
    }

    private String modelXml() {
        return "<response><result-code>0</result-code><extra name=\"balance\">100.00</extra></response>";
    }

}
