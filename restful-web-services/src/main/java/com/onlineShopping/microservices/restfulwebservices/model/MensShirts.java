package com.onlineShopping.microservices.restfulwebservices.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Schema(description = "Mens Shirts Product Model Information")
@Setter
@Getter
@Builder
@Document(collection = "mens_shirts")
public class MensShirts {
    @Id
    private String id;
    @Schema(description = "product name")
    @NotEmpty
    @Size(min = 3,message = "name should have at least 2 characters")
    private String name;
    @Schema(description = "product brand name")
    @NotEmpty
    @Size(min = 3,message = "brand name should have at least 2 characters")
    private String brand;
    @Schema(description = "product short description")
    @NotEmpty
    @Size(min = 10,message = "description should have at least 10 characters")
    private String description;
    @NotEmpty
    @Schema(description = "product price information")
    private Price price;
    @NotEmpty
    @Schema(description = "product inventory details")
    private Inventory inventory;
    @NotEmpty
    @Schema(description = "product attributes")
    private List<Attributes> attributes;
}
