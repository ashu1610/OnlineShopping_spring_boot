package com.onlineShopping.microservices.restfulwebservices.controller;

import com.onlineShopping.microservices.restfulwebservices.model.ResponseHeader;
import com.onlineShopping.microservices.restfulwebservices.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

@Configuration
public class ResponseHandler {

    private ResponseMessage responseMessage;

    private Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public ResponseMessage responseBuilder(HttpStatus httpStatus, Object responseObject ){
        responseMessage= ResponseMessage.builder()
                .header(ResponseHeader.builder()
                        .code(String.valueOf(httpStatus.value()))
                        .timeStamp(new Timestamp(System.currentTimeMillis()))
                        .traceId(MDC.get("traceId"))
                        .build())
                .data(responseObject)
                .build();
        logger.info("response object -> {}",responseMessage);
        return responseMessage;
    }
}
