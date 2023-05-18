package com.onlineShopping.microservices.restfulwebservices.Exception;

import com.onlineShopping.microservices.restfulwebservices.controller.ResponseHandler;
import com.onlineShopping.microservices.restfulwebservices.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(NotFoundExceptionHandler.class);
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        ResponseMessage responseMessage = new ResponseHandler().responseBuilder(HttpStatus.NOT_FOUND,e.getMessage());
        logger.error("Exception handler -> {}",responseMessage);
        return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
    }
}
