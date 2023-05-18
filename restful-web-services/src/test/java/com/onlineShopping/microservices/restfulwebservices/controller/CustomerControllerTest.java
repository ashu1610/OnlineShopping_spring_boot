package com.onlineShopping.microservices.restfulwebservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlineShopping.microservices.restfulwebservices.Exception.ApiRequestException;
import com.onlineShopping.microservices.restfulwebservices.Exception.NotFoundException;
import com.onlineShopping.microservices.restfulwebservices.model.*;
import com.onlineShopping.microservices.restfulwebservices.service.ElectronicsService;
import com.onlineShopping.microservices.restfulwebservices.service.MensShirtsService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElectronicsService electronicsService;

    @MockBean
    private MensShirtsService mensShirtsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Electronics electronics;

    private Electronics electronics1;

    private MensShirts mensShirts;
    private MensShirts mensShirts1;

    @BeforeEach
    public void setup(){
        electronics = Electronics.builder()
                .id("1a")
                .name("phone")
                .brand("brand name")
                .description("small in size smart phone")
                .price(Price.builder().currency("USD").amount(999.99).build())
                .inventory(Inventory.builder().total(40).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("white").build()))
                .build();

        electronics1= Electronics.builder()
                .id("2a")
                .name("watch")
                .brand("brand name")
                .description("small in size smart watch")
                .price(Price.builder().currency("USD").amount(399.99).build())
                .inventory(Inventory.builder().total(20).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("white").build()))
                .build();

        mensShirts = MensShirts.builder()
                .id("1a")
                .name("Men's new strip shirt")
                .brand("brand name")
                .description("small in size smart strips")
                .price(Price.builder().currency("USD").amount(999.99).build())
                .inventory(Inventory.builder().total(40).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("white").build()))
                .build();

        mensShirts1= MensShirts.builder()
                .id("2a")
                .name("Men's color patch shirt")
                .brand("brand name")
                .description("large in size patch work shirt")
                .price(Price.builder().currency("USD").amount(399.99).build())
                .inventory(Inventory.builder().total(20).available(10).reserved(5).build())
                .attributes(List.of(Attributes.builder().name("color").value("red").build()))
                .build();

    }

    @DisplayName("JUnit test for get all electronics with out sort for customer")
    @Test
    public void givenListOfElectronics_whenGetAllElectronics_thenReturnElectronicsList() throws Exception {
        // given
        given(electronicsService.getAllElectronics()).willReturn(List.of(electronics,electronics1));

        // when
        ResultActions response = mockMvc.perform(get("/api/customer/electronics/getAll"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(List.of(electronics,electronics1).size())));
    }

    @DisplayName("JUnit test for get empty electronics with out sort for customer")
    @Test
    public void givenListOfElectronics_whenGetAllElectronics_thenReturnEmptyList() throws Exception {
        // given
        given(electronicsService.getAllElectronics()).willThrow(NotFoundException.class);

        // when
        ResultActions response = mockMvc.perform(get("/api/customer/electronics/getAll"));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    @DisplayName("JUnit test for get all electronics with sort for customer")
    @Test
    public void givenListOfElectronics_whenGetAllElectronics_thenReturnSortedElectronicsList() throws Exception {
        // given
        given(electronicsService.getAllElectronicsWithSortField("name","ASC")).willReturn(List.of(electronics,electronics1));

        // when
        ResultActions response = mockMvc.perform(get("/api/customer/electronics/getAll?sort=name&order=ASC"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(List.of(electronics,electronics1).size())));
    }

    @DisplayName("JUnit test for get empty electronics with sort for customer")
    @Test
    public void givenListOfElectronics_whenGetAllSortedElectronics_thenReturnEmptyList() throws Exception {
        // given
        given(electronicsService.getAllElectronicsWithSortField("name","ASC")).willThrow(NotFoundException.class);

        // when
        ResultActions response = mockMvc.perform(get("/api/customer/electronics/getAll?sort=name&order=ASC"));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("JUnit test for get all mens shirt with out sort for customer")
    @Test
    public void givenListOfMensShirts_whenGetAllMensShirts_thenReturnMensShirtsList() throws Exception {
        // given
        given(mensShirtsService.getAllMensShirts()).willReturn(List.of(mensShirts,mensShirts1));

        // when
        ResultActions response = mockMvc.perform(get("/api/customer/mensShirts/getAll"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(List.of(mensShirts,mensShirts1).size())));
    }

    @DisplayName("JUnit test for get empty mens shirts with out sort for customer")
    @Test
    public void givenListOfMensShirts_whenGetAllMensShirts_thenReturnEmptyList() throws Exception {
        // given
        given(mensShirtsService.getAllMensShirts()).willThrow(NotFoundException.class);

        // when
        ResultActions response = mockMvc.perform(get("/api/customer/mensShirts/getAll"));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("JUnit test for get all mens shirts with sort for customer")
    @Test
    public void givenListOfMensShirts_whenGetAllMensShirts_thenReturnSortedMensShirtsList() throws Exception {
        // given
        given(mensShirtsService.getAllMensShirtsWithSortField("name","ASC")).willReturn(List.of(mensShirts1,mensShirts1));

        // when
        ResultActions response = mockMvc.perform(get("/api/customer/mensShirts/getAll?sort=name&order=ASC"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(List.of(electronics,electronics1).size())));
    }

    @DisplayName("JUnit test for get empty mens shirts with sort for customer")
    @Test
    public void givenListOfMensShirts_whenGetAllSortedMensShirts_thenReturnEmptyList() throws Exception {
        // given
        given(mensShirtsService.getAllMensShirtsWithSortField("name","ASC")).willThrow(NotFoundException.class);

        // when
        ResultActions response = mockMvc.perform(get("/api/customer/mensShirts/getAll?sort=name&order=ASC"));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("JUnit test for get all unknown product for customer")
    @Test
    public void givenEmpty_whenGetAllUnknownProduct_thenReturnMessage() throws Exception {
        // given
        given(electronicsService.getAllElectronics()).willThrow(ApiRequestException.class);

        // when
        ResultActions response = mockMvc.perform(get("/api/customer/ss/getAll"));

        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }
}
