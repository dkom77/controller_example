package com.example.balance.service.exceptions;

import com.example.balance.service.model.Response;

public abstract class ResponseAwareAbstractException extends RuntimeException{
    protected Response response;

    public ResponseAwareAbstractException(String message) {
        super(message);
    }

    public ResponseAwareAbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseAwareAbstractException(Response response) {
        super();
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }
}
