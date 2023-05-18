package com.onlineShopping.microservices.restfulwebservices.controller;

import com.onlineShopping.microservices.restfulwebservices.Exception.ApiRequestException;
import com.onlineShopping.microservices.restfulwebservices.Exception.NotFoundException;
import com.onlineShopping.microservices.restfulwebservices.model.Electronics;
import com.onlineShopping.microservices.restfulwebservices.model.MensShirts;
import com.onlineShopping.microservices.restfulwebservices.model.ResponseMessage;
import com.onlineShopping.microservices.restfulwebservices.service.ElectronicsService;
import com.onlineShopping.microservices.restfulwebservices.service.MensShirtsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(
        name = "Admin Service - AdminController",
        description = "AdminController Exposes REST APIs for Admin Services"
)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    ElectronicsService electronicsService;

    @Autowired
    MensShirtsService mensShirtsService;

    @Autowired
    private ResponseHandler responseHandler;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Operation(
            summary = "Get All Product Category REST API",
            description = "Admin will get all stock for selected Products Category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 Get All Details"
    )
    @GetMapping("/{category}/getAll")
    public ResponseMessage getAllProducts(@PathVariable String category){
        logger.info("get products for admin -> {}",category);
        if(category.equals("electronics")){
            List<Electronics> electronicsList = electronicsService.getAllElectronicsWithoutFilter();
            if(electronicsList.isEmpty()){
                throw new NotFoundException("Electronics Products are not present !!!");
            }
            return responseHandler.responseBuilder(HttpStatus.OK,electronicsList);
        } else if (category.equals("mensShirts")) {
            List<MensShirts> mensShirtsList = mensShirtsService.getAllMensShirtsWithoutFilter();
            if(mensShirtsList.isEmpty()){
                throw new NotFoundException("Mens Shirts Products are not present !!!");
            }
            return responseHandler.responseBuilder(HttpStatus.OK,mensShirtsList);
        } else {
            throw new ApiRequestException("Bad Api Request Or Invalid Product Category !!!");
        }
    }

    @Operation(
            summary = "Create Product REST API",
            description = "Admin will able to create Product under Electronic Category"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 created"
    )
    @PostMapping("/electronics/create")
    public ResponseMessage createElectronics(@Valid @RequestBody Electronics electronics) {
        logger.info("create electronics products -> {}",electronics);
        return responseHandler.responseBuilder(HttpStatus.CREATED,electronicsService.createElectronics(electronics));
    }

    @Operation(
            summary = "Create Mens Shirts Product REST API",
            description = "Admin will able to create Product under Mens Shirts Category"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 created"
    )
    @PostMapping("/mensShirts/create")
    public ResponseMessage createMensShirts(@Valid @RequestBody MensShirts mensShirts){
        logger.info("create mensShirts products -> {}",mensShirts);
        return responseHandler.responseBuilder(HttpStatus.CREATED,mensShirtsService.createMensShirts(mensShirts));
    }

    @Operation(
            summary = "Create Unknown Product REST API",
            description = "Admin will not able to create Product under Unknown Category"
    )
    @ApiResponse(
            responseCode = "404",
            description = "HTTP Status 404 Category Not Found"
    )
    @PostMapping("/{category}/create")
    public void allDefaultCreate(){
        logger.info("create unknown products");
        throw new ApiRequestException("Bad Api Request Or Invalid Product Category !!!");
    }

    @Operation(
            summary = "Delete Electronic Product REST API",
            description = "Admin will able to delete Product under Electronic Category"
    )
    @ApiResponse(
            responseCode = "202",
            description = "HTTP Status 202 Accepted"
    )
    @DeleteMapping("/{category}/delete/{id}")
    public ResponseMessage deleteProduct(@PathVariable String category,@PathVariable String Id){
        logger.info("delete products -> {}",List.of(category,Id));
        if(category.equals("electronics")){
            Optional<Electronics> itemFound = electronicsService.findElectronicsById(Id);
            if(itemFound.isEmpty()){
                throw new NotFoundException("Electronics Product is not present !!!");
            }
            electronicsService.deleteElectronics(Id);
            return responseHandler.responseBuilder(HttpStatus.ACCEPTED,"Item has been deleted");
        } else if (category.equals("mensShirts")) {
            Optional<MensShirts> itemFound = mensShirtsService.findMensShirtsById(Id);
            if(itemFound.isEmpty()){
                throw new NotFoundException("Electronics Product is not present, please add new !!!");
            }
            mensShirtsService.deleteMensShirts(Id);
            return responseHandler.responseBuilder(HttpStatus.ACCEPTED,"Item has been deleted");
        } else {
            throw new ApiRequestException("Bad Api Request Or Invalid Product Category !!!");
        }
    }

    @Operation(
            summary = "Update Electronic Product REST API",
            description = "Admin will able to Update Certain Product details under Electronic Category"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 Update"
    )
    @PutMapping("/electronics/update")
    public ResponseMessage updateElectronics(@Valid @RequestBody Electronics electronics){
        logger.info("update electronics product -> {}",electronics);
        Optional<Electronics> itemFound = electronicsService.findElectronicsById(electronics.getId());
        if(itemFound.isEmpty()){
            throw new NotFoundException("Electronics Product is not present, please add new !!!");
        }
        return responseHandler.responseBuilder(HttpStatus.NO_CONTENT,electronicsService.createElectronics(electronics));
    }

    @Operation(
            summary = "Update Mens Shirts Product REST API",
            description = "Admin will able to Update Certain Product details under Mens Shirts Category"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 Update"
    )
    @PutMapping("/mensShirts/update")
    public ResponseMessage updateMensShirts(@Valid @RequestBody MensShirts mensShirts){
        logger.info("update mensShirts product -> {}",mensShirts);
        Optional<MensShirts> itemFound = mensShirtsService.findMensShirtsById(mensShirts.getId());
        if(itemFound.isEmpty()){
            throw new NotFoundException("Electronics Product is not present, please add new !!!");
        }
        return responseHandler.responseBuilder(HttpStatus.NO_CONTENT,mensShirtsService.createMensShirts(mensShirts));
    }

    @Operation(
            summary = "Update Unknown Product REST API",
            description = "Admin will able to Update Certain Product details under Unknown Category"
    )
    @ApiResponse(
            responseCode = "404",
            description = "HTTP Status 404 Not Found"
    )
    @PutMapping("/{category}/update")
    public void allDefaultUpdate(){
        logger.info("update unknown product");
        throw new ApiRequestException("Bad Api Request Or Invalid Product Category !!!");
    }
}
