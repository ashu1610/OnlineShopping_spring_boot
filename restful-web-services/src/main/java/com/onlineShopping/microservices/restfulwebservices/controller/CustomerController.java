package com.onlineShopping.microservices.restfulwebservices.controller;

import com.onlineShopping.microservices.restfulwebservices.Exception.ApiRequestException;
import com.onlineShopping.microservices.restfulwebservices.model.Electronics;
import com.onlineShopping.microservices.restfulwebservices.model.MensShirts;
import com.onlineShopping.microservices.restfulwebservices.model.ResponseMessage;
import com.onlineShopping.microservices.restfulwebservices.service.ElectronicsService;
import com.onlineShopping.microservices.restfulwebservices.service.MensShirtsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Customer Service - CustomerController",
        description = "CustomerController Exposes REST APIs for Customer Services"
)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    ElectronicsService electronicsService;

    @Autowired
    MensShirtsService mensShirtsService;

    @Autowired
    private ResponseHandler responseHandler;

    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Operation(
            summary = "Get All Product REST API",
            description = "Customer will get all available stock for selected product category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Get All Details"
    )
    @ApiResponse(
            responseCode = "404",
            description = "HTTP Status 404 Product went out of stocks"
    )
    @GetMapping("/{category}/getAll")
    public ResponseMessage getAllProducts(
            @PathVariable String category,
            @RequestParam("sort") Optional<String> sortField,
            @RequestParam("order") Optional<String> sortOrder){
        logger.info("Into customer get all -> {}",List.of(category,sortField,sortOrder));
        if (category.equals("electronics") && sortField.isPresent()) {
            String order = sortOrder.orElse("ASC");
            List<Electronics> electronicsList = electronicsService.getAllElectronicsWithSortField(sortField.get(), order);
            if(electronicsList.isEmpty()){
                throw new NotFoundException("Electronics Products are out of stocks !!!");
            }
            return responseHandler.responseBuilder(HttpStatus.OK,electronicsList);
        } else if (category.equals("electronics")) {
            List<Electronics> electronicsList = electronicsService.getAllElectronics();
            if(electronicsList.isEmpty()){
                throw new NotFoundException("Electronics Products are out of Stocks !!!");
            }
            return responseHandler.responseBuilder(HttpStatus.OK,electronicsList);
        } else if (category.equals("mensShirts") && sortField.isPresent()) {
            String order = sortOrder.orElse("ASC");
            List<MensShirts> mensShirtsList = mensShirtsService.getAllMensShirtsWithSortField(sortField.get(),order);
            if(mensShirtsList.isEmpty()){
                throw new NotFoundException("Mens Shirts Products are out of stocks !!!");
            }
            return responseHandler.responseBuilder(HttpStatus.OK,mensShirtsList);
        } else if (category.equals("mensShirts")) {
            List<MensShirts> mensShirtsList = mensShirtsService.getAllMensShirts();
            if(mensShirtsList.isEmpty()){
                throw new NotFoundException("Mens Shirts Products are out of stocks !!!");
            }
            return responseHandler.responseBuilder(HttpStatus.OK,mensShirtsList);
        } else {
            throw new ApiRequestException("Bad Api Request Or Invalid Product Category !!!");
        }
    }
}
