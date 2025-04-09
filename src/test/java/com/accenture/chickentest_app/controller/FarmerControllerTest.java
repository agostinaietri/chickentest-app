package com.accenture.chickentest_app.controller;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Egg;
import jakarta.validation.Valid;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.accenture.chickentest_app.Controller.FarmerController;
import com.accenture.chickentest_app.dto.FarmerDTO;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.service.FarmerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(FarmerController.class)
public class FarmerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FarmerService farmerService;

    private FarmerDTO farmerDto;
    private Farmer farmer;

    @MockBean
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        // Test data
        farmerDto = new FarmerDTO("Arthur", 100, 10);

        farmer = new Farmer();
        farmer.setName("Arthur");
        farmer.setBalance(100);
        farmer.setFarmLimit(10);
    }

    @Test
    @Order(1)
    public void saveFarmerTest() throws Exception {
        //given
        when(modelMapper.map(any(FarmerDTO.class), eq(Farmer.class))).thenReturn(farmer);
        doNothing().when(farmerService).addFarmer(any(Farmer.class));

        //when
        mockMvc.perform(post("/api/v1/farmer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(farmerDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Farmer successfully added"));

        // then
        verify(farmerService, times(1)).addFarmer(any(Farmer.class));
    }

    @Test
    @Order(2)
    public void getFarmerTest() throws Exception{
        // given
        List<Farmer> farmerList = new ArrayList<>();
        long farmerId = 1L;
        new Farmer();
        Farmer farmer1;
        farmer1 = new Farmer();
        farmer1.setId(farmerId);
        farmer1.setName("Arthur");
        farmer1.setBalance(100);
        farmer1.setFarmLimit(10);
        farmerList.add(farmer1);
        BDDMockito.given(farmerService.getFarmers()).willReturn(farmerList);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/farmer"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(1)));

    }

    @Test
    @Order(3)
    public void whenFarmerListEmpty_GetFarmerTestFails() throws Exception{
        // given
        List<Farmer> farmerList = new ArrayList<>();
        BDDMockito.given(farmerService.getFarmers()).willReturn(farmerList);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/farmer"))
                .andExpect(status().isBadRequest());

        // then
        verify(farmerService, times(1)).getFarmers();

    }

    @Test
    @Order(4)
    public void whenFarmerInvalidId_GetFarmerTestFails() throws Exception{
        long farmerId = 1L;
        new Farmer();
        Farmer farmer1;
        farmer1 = new Farmer();
        farmer1.setId(farmerId);
        farmer1.setName("Arthur");
        farmer1.setBalance(100);
        farmer1.setFarmLimit(10);
        BDDMockito.given(farmerService.findFarmerById(farmerId)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(get("/api/v1/farmer/get").param("id", String.valueOf(farmer1.getId())));


        // then - verify the output
        response.andExpect(status().isBadRequest())
                .andDo(print());

    }
    @Test
    @Order(5)
    public void getByIdFarmerTest() throws Exception{
        // precondition
        long farmerId = 1L;
        new Farmer();
        Farmer farmer1;
        farmer1 = new Farmer();
        farmer1.setId(farmerId);
        farmer1.setName("Arthur");
        farmer1.setBalance(100);
        farmer1.setFarmLimit(10);
        BDDMockito.given(farmerService.findFarmerById(farmerId)).willReturn(Optional.of((farmer1)));

        // action
        mockMvc.perform(get("/api/v1/farmer/get").param("id", String.valueOf(farmer1.getId())));

        // verify
        verify(farmerService, times(1)).getFarmer(farmer1.getId());

    }

    @Test
    @Order(6)
    public void updateFarmerTest() throws Exception{
        long farmerId = 1L;
        Farmer savedFarmer = Farmer.builder()
                .id(1L)
                .name("John")
                .balance(100)
                .farmLimit(10)
                .build();

        Farmer updatedFarmer = Farmer.builder()
                .id(2L)
                .name("Arthur")
                .balance(50)
                .farmLimit(5)
                .build();

        BDDMockito.given(farmerService.findFarmerById(farmerId)).willReturn(Optional.of(savedFarmer));
        BDDMockito.doAnswer(invocation -> ResponseEntity.ok("Farmer updated successfully."))
                .when(farmerService).updateFarmer(anyLong(), any(Farmer.class));

        // action
        ResultActions response = mockMvc.perform(put("/api/v1/farmer/update/"  + farmerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedFarmer)));

        // verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").value("Farmer updated successfully."));
    }

    @Test
    @Order(7)
    public void deleteFarmerTest() throws Exception{
        // given
        long farmerId = 1L;
        willDoNothing().given(farmerService).deleteFarmer(farmerId);

        // when
        mockMvc.perform(delete("/api/v1/farmer/delete/" + farmerId))
                .andExpect(status().isNoContent());

        // then
        verify(farmerService, times(1)).deleteFarmer(farmerId);
    }

    @Test
    @Order(8)
    public void sellChickenTest() throws Exception {
        List<Long> chickenIds = new ArrayList<>();

        Farmer existingFarmer = new Farmer();
        existingFarmer.setId(1L);
        existingFarmer.setBalance(500);
        existingFarmer.setFarmLimit(15);
        existingFarmer.setCattle(10);
        existingFarmer.setChickenQuantity(10);

        when(farmerService.sellChicken(anyList(), eq(1L))).thenReturn(true);

        ResultActions response = mockMvc.perform(delete("/api/v1/farmer/sell/chicken/"  + existingFarmer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chickenIds)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Chicken sold successfully."))
                .andDo(print());
    }

    @Test
    @Order(8)
    public void buyChickenTest() throws Exception {
        long farmerId = 1L;
        List<Chicken> chickens = Arrays.asList(
                new Chicken(),
                new Chicken()
        );
        chickens.get(0).setPrice(100);
        chickens.get(1).setPrice(150);

        Farmer existingFarmer = new Farmer();
        existingFarmer.setId(1L);
        existingFarmer.setBalance(500);
        existingFarmer.setFarmLimit(10);
        existingFarmer.setCattle(0);
        existingFarmer.setChickenQuantity(0);

        when(farmerService.buyChicken(anyList(), eq(1L))).thenReturn(true);

        // action
        ResultActions response = mockMvc.perform(post("/api/v1/farmer/buy/chicken/"  + farmerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(chickens)));

        response.andExpect(status().is2xxSuccessful())
                .andExpect(content().string("Chicken purchased successfully."))
                .andDo(print());
    }
}