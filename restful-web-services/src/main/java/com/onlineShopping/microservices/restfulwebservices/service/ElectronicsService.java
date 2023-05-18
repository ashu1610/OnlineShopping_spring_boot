package com.onlineShopping.microservices.restfulwebservices.service;

import com.onlineShopping.microservices.restfulwebservices.model.Electronics;
import com.onlineShopping.microservices.restfulwebservices.repository.ElectronicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectronicsService {

    ElectronicsRepository electronicsRepository;

    public ElectronicsService(ElectronicsRepository electronicsRepository) {
        this.electronicsRepository = electronicsRepository;
    }

    public static boolean validToShow(Electronics electronic){
        return electronic.getInventory().getAvailable() > electronic.getInventory().getReserved();
    }

    public List<Electronics> getAllElectronics(){
        List<Electronics> electronicsList = electronicsRepository.findAll();
        return electronicsList.stream().filter(ElectronicsService::validToShow).toList();
    }

    public  List<Electronics> getAllElectronicsWithSortField(String sortField, String sortOrder){
        Sort.Direction direction = sortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        List<Electronics> electronicsList = electronicsRepository.findAll(sort);
        return electronicsList.stream().filter(ElectronicsService::validToShow).toList();
    }

    public  List<Electronics> getAllElectronicsWithoutFilter(){
        return electronicsRepository.findAll();
    }

    public Electronics createElectronics(Electronics electronics){
        return electronicsRepository.save(electronics);
    }

    public void deleteElectronics(String id){ electronicsRepository.deleteById(id);}

   public Optional<Electronics> findElectronicsById(String Id){ return electronicsRepository.findById(Id);}
}
