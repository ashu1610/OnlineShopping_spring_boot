package com.onlineShopping.microservices.restfulwebservices.model;

import java.sql.Timestamp;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Setter
@Getter
@Builder
public class ResponseHeader {
    private String code;
    private Timestamp timeStamp;
    private String traceId;
}
