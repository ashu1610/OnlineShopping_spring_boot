package com.onlineShopping.microservices.restfulwebservices.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Schema(description = "Price Model Information")
@Setter
@Getter
@Builder
public class Price {
    @Schema(description = "Price currency")
    @NotEmpty
    private String currency;
    @Schema(description = "Price amount value")
    @NotNull
    @Range(min = 1L,message = "enter a valid amount")
    private Double amount;
}
