package com.example.balance.service.controller.validation;

import com.example.balance.service.model.Request;
import com.example.balance.service.model.RequestType;

public interface RequestTypeValidator {
    boolean isValid(Request request);
    RequestType willValidate();
}
