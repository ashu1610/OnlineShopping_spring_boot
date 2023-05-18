package com.onlineShopping.microservices.restfulwebservices.service;

import com.onlineShopping.microservices.restfulwebservices.model.Attributes;
import com.onlineShopping.microservices.restfulwebservices.model.Electronics;
import com.onlineShopping.microservices.restfulwebservices.model.Inventory;
import com.onlineShopping.microservices.restfulwebservices.model.Price;
import com.onlineShopping.microservices.restfulwebservices.repository.ElectronicsRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ElectronicsServiceTest {
    @Mock
    private ElectronicsRepository electronicsRepository;

    @InjectMocks
    private ElectronicsService electronicsService;

    private Electronics electronics;

    @BeforeEach
    public void setup(){
        electronics = Electronics.builder()
                .id("1a")
                .name("watch")
                .brand("brand name")
                .description("small in size smart watch")
                .price(Price.builder().currency("USD").amount(399.99).build())
                .inventory(Inventory.builder().total(20).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("grey").build()))
                .build();
    }

    @DisplayName("JUnit test for search by id electronics positive case")
    @Test
    public void givenElectronicObjectId_whenFindElectronicById_thenReturnElectronic(){
        // given
        given(electronicsRepository.findById(electronics.getId())).willReturn(Optional.ofNullable(electronics));

        //when
        Optional<Electronics> response = electronicsService.findElectronicsById(electronics.getId());

        //then
        assertThat(response).isNotNull();
    }

    @DisplayName("JUnit test for search by id electronics negative case")
    @Test
    public void givenElectronicObjectId_whenFindElectronicById_thenReturnNothing(){
        // given
        given(electronicsRepository.findById(electronics.getId())).willReturn(Optional.empty());

        //when
        Optional<Electronics> response = electronicsService.findElectronicsById(electronics.getId());

        //then
        assertThat(response).isEmpty();
    }

    @DisplayName("JUnit test for create electronics")
    @Test
    public void givenElectronicObject_whenSaveElectronic_thenReturnElectronic(){
        // given
        given(electronicsRepository.save(electronics)).willReturn(electronics);

        //when
        Electronics createElectronics = electronicsService.createElectronics(electronics);

        //then
        assertThat(createElectronics).isNotNull();
    }

    @DisplayName("JUnit test for delete electronics")
    @Test
    public void givenElectronicId_whenDeleteElectronic_thenReturnMessage(){
        // given
        String id = "1aad1ssj1fh347hfd";
        willDoNothing().given(electronicsRepository).deleteById(id);

        //when
        electronicsService.deleteElectronics(id);

        //then
        verify(electronicsRepository,times(1)).deleteById(id);
    }

    @DisplayName("JUnit test for update electronics")
    @Test
    public void givenElectronicObject_whenUpdateElectronic_thenReturnUpdatedElectronics(){
        // given
        given(electronicsRepository.save(electronics)).willReturn(electronics);
        electronics.setName("Smart watch");
        electronics.setBrand("brand Apple");

        //when
        Electronics updatedElectronics = electronicsService.createElectronics(electronics);

        //then
        assertThat(updatedElectronics.getName()).isEqualTo("Smart watch");
        assertThat(updatedElectronics.getBrand()).isEqualTo("brand Apple");
    }

    @DisplayName("JUnit test for positive get all without sort electronics for customer")
    @Test
    public void givenElectronicList_whenGetAllElectronic_thenReturnElectronicsList(){
        // given
        Electronics electronics1 = Electronics.builder()
                .id("2a")
                .name("phone")
                .brand("brand name")
                .description("small in size smart phone")
                .price(Price.builder().currency("USD").amount(999.99).build())
                .inventory(Inventory.builder().total(40).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("white").build()))
                .build();
        given(electronicsRepository.findAll()).willReturn(List.of(electronics,electronics1));

        //when
        List<Electronics> electronicsList = electronicsService.getAllElectronics();

        //then
        assertThat(electronicsList).isNotNull();
        assertThat(electronicsList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for negative get all customer without sort electronics")
    @Test
    public void givenElectronicList_whenGetAllElectronic_thenReturnEmptyList(){
        // given
        Electronics electronics1 = Electronics.builder()
                .id("2a")
                .name("phone")
                .brand("brand name")
                .description("small in size smart phone")
                .price(Price.builder().currency("USD").amount(999.99).build())
                .inventory(Inventory.builder().total(40).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("white").build()))
                .build();
        given(electronicsRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Electronics> electronicsList = electronicsService.getAllElectronics();

        //then
        assertThat(electronicsList).isEmpty();
        assertThat(electronicsList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for positive get all with sort on name electronics for customer")
    @Test
    public void givenElectronicList_whenGetAllElectronicSorted_thenReturnElectronicsList(){
        // given
        Electronics electronics1 = Electronics.builder()
                .id("2a")
                .name("phone")
                .brand("brand name")
                .description("small in size smart phone")
                .price(Price.builder().currency("USD").amount(999.99).build())
                .inventory(Inventory.builder().total(40).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("white").build()))
                .build();
        String sortFiled = "name";
        String sortOrder = "ASC";
        Sort sort = Sort.by(Sort.Direction.ASC, sortFiled);
        given(electronicsRepository.findAll(sort)).willReturn(List.of(electronics1,electronics));

        //when
        List<Electronics> electronicsList = electronicsService.getAllElectronicsWithSortField(sortFiled,sortOrder);

        //then
        assertThat(electronicsList).isNotNull();
        assertThat(electronicsList.size()).isEqualTo(2);
        assertThat(electronicsList.get(0).getName()).isEqualTo("phone");
    }

    @DisplayName("JUnit test for negative get all customer with sort on name electronics")
    @Test
    public void givenElectronicList_whenGetAllElectronicSorted_thenReturnEmptyList(){
        // given
        Electronics electronics1 = Electronics.builder()
                .id("2a")
                .name("phone")
                .brand("brand name")
                .description("small in size smart phone")
                .price(Price.builder().currency("USD").amount(999.99).build())
                .inventory(Inventory.builder().total(40).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("white").build()))
                .build();
        String sortFiled = "name";
        String sortOrder = "ASC";
        Sort sort = Sort.by(Sort.Direction.ASC, sortFiled);
        given(electronicsRepository.findAll(sort)).willReturn(Collections.emptyList());

        //when
        List<Electronics> electronicsList = electronicsService.getAllElectronicsWithSortField(sortFiled,sortOrder);

        //then
        assertThat(electronicsList).isEmpty();
        assertThat(electronicsList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for get all electronics for admin")
    @Test
    public void givenElectronicList_whenGetAllElectronicWithNoLimit_thenReturnElectronicsList(){
        // given
        Electronics electronics1 = Electronics.builder()
                .id("2a")
                .name("phone")
                .brand("brand name")
                .description("small in size smart phone")
                .price(Price.builder().currency("USD").amount(999.99).build())
                .inventory(Inventory.builder().total(40).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("white").build()))
                .build();
        given(electronicsRepository.findAll()).willReturn(List.of(electronics,electronics1));

        //when
        List<Electronics> electronicsList = electronicsService.getAllElectronicsWithoutFilter();

        //then
        assertThat(electronicsList).isNotNull();
        assertThat(electronicsList.size()).isEqualTo(2);
    }
}
