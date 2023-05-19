package com.onlineShopping.microservices.restfulwebservices.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Inventory Model Information")
@Setter
@Getter
@Builder
public class Inventory {
    @Schema(description = "total number of stock")
    @NotNull
    @Min(value=0,message = "enter valid inventory total")
    private int total;
    @Schema(description = "available stock for customer")
    @NotNull
    @Min(value=0,message = "enter valid inventory available")
    private int available;
    @Schema(description = "reserved stock in inventory")
    @NotNull
    @Min(value = 0,message = "enter valid inventory reserved")
    private int reserved;
}
