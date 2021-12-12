package com.example.balance.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Objects;

@JacksonXmlRootElement(localName = "response")
@JsonPropertyOrder({ "code", "extras" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    @JacksonXmlProperty(localName = "result-code")
    private ResponseCode code;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "extra")
    private List<Extra> extras;

    public Response(ResponseCode code, List<Extra> extras) {
        this.code = code;
        this.extras = extras;
    }

    public Response() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return code == response.code && extras.equals(response.extras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, extras);
    }
}
