package com.example.balance.service.exceptions;

import com.example.balance.service.model.Response;

public class RequestTargetException extends ResponseAwareAbstractException{

    public RequestTargetException(String message) {
        super(message);
    }

    public RequestTargetException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestTargetException(Response response) {
        super(response);
    }
}
