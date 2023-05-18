package com.onlineShopping.microservices.restfulwebservices;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@OpenAPIDefinition(
		info = @Info(
				title = "Online Shopping REST APIs",
				description = " Online Shopping REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name="Ashish Yadav",
						email="com.onlineShopping.microservice"
				),
				license = @License(
						name = "Apache 2.0",
						url = "onlineShopping.net/license"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Products Service Doc",
				url = "onlineShopping.net/products_service.html"
		)
)
@SpringBootApplication
@EnableMongoRepositories("com.onlineShopping.microservices.restfulwebservices.repository")
@ComponentScan("com.onlineShopping.microservices.restfulwebservices.*")
public class RestfulWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}

}
