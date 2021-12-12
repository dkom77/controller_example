package com.example.balance.service.controller;

import com.example.balance.service.exceptions.ClientCreationException;
import com.example.balance.service.exceptions.ClientNotFoundException;
import com.example.balance.service.exceptions.RequestTargetException;
import com.example.balance.service.exceptions.ResponseAwareAbstractException;
import com.example.balance.service.model.Response;
import com.example.balance.service.model.ResponseCode;
import com.example.balance.service.util.ResponseComposer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);
    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String bodyOfResponse = generateBody(ResponseComposer.compose(
                ResponseCode.TECHNICAL_ERROR,"request validation error"));

        headers.add("Content-Type", "application/xml");

        log.warn("{} exception: {}", ex.getClass().getSimpleName(), bodyOfResponse);

        return handleExceptionInternal(ex, bodyOfResponse,
                headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Response response = ResponseComposer.compose(ResponseCode.TECHNICAL_ERROR, ex.getMessage());
        String bodyOfResponse = generateBody(response);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");

        log.warn("{} exception: {}", ex.getClass().getSimpleName(), bodyOfResponse);

        return handleExceptionInternal(ex, bodyOfResponse,
                headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { ClientCreationException.class})
    protected ResponseEntity<Object> handleClientCreationException(RuntimeException ex, WebRequest request) {

        Response response = ((ResponseAwareAbstractException) ex).getResponse();
        String bodyOfResponse = generateBody(response);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");

        log.warn("{} exception: {}", ex.getClass().getSimpleName(), bodyOfResponse);

        return handleExceptionInternal(ex, bodyOfResponse,
                headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {
            RequestTargetException.class,
            ClientNotFoundException.class
    })
    protected ResponseEntity<Object> handleRequestTargetException(
            RuntimeException ex, WebRequest request) {

        Response response = ((ResponseAwareAbstractException) ex).getResponse();
        String bodyOfResponse = generateBody(response);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/xml");
        log.warn("{} exception: {}", ex.getClass().getSimpleName(), bodyOfResponse);

        return handleExceptionInternal(ex, bodyOfResponse,
                headers, HttpStatus.BAD_REQUEST, request);
    }

    private String generateBody(Response response) {
        try {
            return xmlMapper.writeValueAsString(response);
        } catch (JsonProcessingException jpe) {
            log.warn("Failed to serialize response {}", jpe.getMessage());
            return "Failed to serialize response";
        }
    }
}
