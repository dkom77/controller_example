package com.example.balance.service.controller.validation;

import com.example.balance.service.model.Request;
import com.example.balance.service.model.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestValidator implements ConstraintValidator<IsValidRequest, Request> {

    private final List<RequestTypeValidator> validatorsList;
    private Map<RequestType, RequestTypeValidator> validators;

    @Autowired
    public RequestValidator(List<RequestTypeValidator> validatorsList) {
        this.validatorsList = validatorsList;
    }

    @PostConstruct
    public void init() {
        validators = validatorsList.stream()
                .collect(Collectors.toMap(RequestTypeValidator::willValidate, Function.identity()));
    }

    @Override
    public boolean isValid(Request value, ConstraintValidatorContext context) {
        RequestType requestType = value.getRequestType();
        if (requestType == null || !validators.containsKey(requestType)) {
            return false;
        }

        return validators.get(requestType).isValid(value);
    }

    @Override
    public void initialize(IsValidRequest constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

}
