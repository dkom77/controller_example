package com.example.balance.service.controller;

import com.example.balance.service.exceptions.RequestTargetException;
import com.example.balance.service.model.Request;
import com.example.balance.service.model.RequestType;
import com.example.balance.service.model.Response;
import com.example.balance.service.model.ResponseCode;
import com.example.balance.service.service.ClientService;
import com.example.balance.service.util.ResponseComposer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MainController {
    private final static Logger log = LoggerFactory.getLogger(MainController.class);

    private final ClientService clientService;


    @Autowired
    public MainController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "/register",
            consumes = {MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE})
    public Response registerClient(@Valid @RequestBody Request request) {
        checkRequestTarget(request, RequestType.CREATE_AGT);
        return clientService.registerNewUser(request);
    }

    @PostMapping(value = "/balance",
            consumes = {MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE})
    public Response getClientBalance(@Valid @RequestBody Request request) {
        checkRequestTarget(request, RequestType.GET_BALANCE);
        return clientService.getBalance(request);
    }

    private void checkRequestTarget(Request request, RequestType targetType) {
        if (targetType != request.getRequestType()) {
            String errMsg = "Wrong request type";
            log.warn("wrong request type for request {} ", request);

            throw new RequestTargetException(ResponseComposer.compose(ResponseCode.TECHNICAL_ERROR, errMsg));
        }
    }
}
