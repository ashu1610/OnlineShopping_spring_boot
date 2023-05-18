package com.onlineShopping.microservices.restfulwebservices.model;

import lombok.*;

import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage {
    private ResponseHeader header;
    private Object data;
}
