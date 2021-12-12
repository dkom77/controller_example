package com.example.balance.service.model;

import com.example.balance.service.controller.validation.IsValidRequest;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Objects;

@JacksonXmlRootElement(localName = "request")
@JsonPropertyOrder({ "requestType", "extras" })
@IsValidRequest
public class Request {
    @JacksonXmlProperty(localName = "request-type")
    private RequestType requestType;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "extra")
    private List<Extra> extras;

    public Request(RequestType requestType, List<Extra> extras) {
        this.requestType = requestType;
        this.extras = extras;
    }

    public Request() {
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType=" + requestType +
                ", extras=" + extras +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return requestType == request.requestType && extras.equals(request.extras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestType, extras);
    }
}
