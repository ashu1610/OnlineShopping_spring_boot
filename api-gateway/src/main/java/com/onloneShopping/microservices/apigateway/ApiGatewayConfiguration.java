package com.onloneShopping.microservices.apigateway;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){

        return builder.routes()
                .route(p-> p.path("/api/**")
                        .uri("lb://products"))
                .build();
    }
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Bean
//    public Object gatewayRouter(RouteLocatorBuilder builder){
//        Object sample = restTemplate.getForObject(builder.routes().route(p-> p.path("/api/**")
//                        .uri("lb://products"))
//                .build().toString(), Object.class);
//        return sample;
//    }
}
