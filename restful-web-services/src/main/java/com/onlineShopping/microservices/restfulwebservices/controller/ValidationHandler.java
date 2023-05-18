package com.onlineShopping.microservices.restfulwebservices.controller;

import com.onlineShopping.microservices.restfulwebservices.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ValidationHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errorMessage = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName =((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMessage.add(fieldName + " -> "+message);
        });
        ResponseMessage response = new ResponseHandler().responseBuilder(HttpStatus.BAD_REQUEST,errorMessage);
        logger.error("error msg -> {}",response);
        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }
}
