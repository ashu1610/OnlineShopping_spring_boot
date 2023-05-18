package com.onlineShopping.microservices.restfulwebservices.Exception;

import com.onlineShopping.microservices.restfulwebservices.controller.ResponseHandler;
import com.onlineShopping.microservices.restfulwebservices.model.ResponseHeader;
import com.onlineShopping.microservices.restfulwebservices.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@ControllerAdvice
public class ApiExceptionHandler {

    private Logger logger= LoggerFactory.getLogger(ApiExceptionHandler.class);
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        ResponseMessage responseMessage = new ResponseHandler().responseBuilder(HttpStatus.BAD_REQUEST,e.getMessage());
        logger.error("exception handler -> {}",responseMessage);
         return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
    }
}
