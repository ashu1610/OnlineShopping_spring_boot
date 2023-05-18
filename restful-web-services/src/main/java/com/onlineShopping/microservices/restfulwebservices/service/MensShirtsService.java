package com.onlineShopping.microservices.restfulwebservices.service;

import com.onlineShopping.microservices.restfulwebservices.model.MensShirts;
import com.onlineShopping.microservices.restfulwebservices.repository.MensShirtsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensShirtsService {

    @Autowired
    MensShirtsRepository mensShirtsRepository;

    public static boolean validToShow(MensShirts mensShirts){
        return mensShirts.getInventory().getAvailable() > mensShirts.getInventory().getReserved();
    }

    public List<MensShirts> getAllMensShirts(){
        List<MensShirts> mensShirtsList = mensShirtsRepository.findAll();
        return mensShirtsList.stream().filter(MensShirtsService::validToShow).toList();
    }

    public  List<MensShirts> getAllMensShirtsWithSortField(String sortField, String sortOrder){
        Sort.Direction direction = sortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        List<MensShirts> mensShirtsList = mensShirtsRepository.findAll(sort);
        return mensShirtsList.stream().filter(MensShirtsService::validToShow).toList();
    }

    public List<MensShirts> getAllMensShirtsWithoutFilter(){
        return mensShirtsRepository.findAll();
    }

    public MensShirts createMensShirts(MensShirts mensShirts){
        return mensShirtsRepository.save(mensShirts);
    }

    public void deleteMensShirts(String id){
        mensShirtsRepository.deleteById(id);
    }

    public Optional<MensShirts> findMensShirtsById(String Id){
        return mensShirtsRepository.findById(Id);
    }
}
