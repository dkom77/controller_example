package com.example.balance.service.exceptions;

import com.example.balance.service.model.Response;

public class ClientCreationException extends ResponseAwareAbstractException{

    public ClientCreationException(String message) {
        super(message);
    }

    public ClientCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientCreationException(Response response) {
        super(response);
    }
}
