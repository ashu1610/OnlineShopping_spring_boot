package com.onlineShopping.microservices.restfulwebservices.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Attributes Model Information")
@Setter
@Getter
@Builder
public class Attributes {
    @Schema(description = "item attributes name")
    @NotEmpty
    private String name;
    @Schema(description = "item attributes description")
    @NotEmpty
    private String value;
}
