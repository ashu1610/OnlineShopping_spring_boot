package com.onlineShopping.microservices.restfulwebservices.repository;

import com.onlineShopping.microservices.restfulwebservices.model.Electronics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectronicsRepository extends MongoRepository<Electronics, String> {
}
