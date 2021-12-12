package com.example.balance.service.exceptions;

import com.example.balance.service.model.Response;

public class ClientNotFoundException extends ResponseAwareAbstractException{
    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientNotFoundException(Response response) {
        super(response);
    }
}
