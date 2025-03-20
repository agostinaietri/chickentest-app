package com.accenture.chickentest_app.controller;

import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import com.accenture.chickentest_app.Controller.FarmerController;
import com.accenture.chickentest_app.dto.FarmerDTO;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.service.FarmerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.awaitility.Awaitility.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FarmerControllerTest.class)
public class FarmerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private FarmerService farmerService;

    private FarmerDTO farmerDto;
    private Farmer farmer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        farmerDto = new FarmerDTO(
                "Arthur",
                100,
                10
        );

        farmer = Farmer.builder()
                .id(1L)
                .name("John")
                .balance(100)
                .farmLimit(10)
                .build();
        List<Farmer> farmers = List.of(farmer, farmer, farmer);
    }

    @Test
    @Order(1)
    public void saveFarmerTest() throws Exception{
        // precondition
        farmerDto = new FarmerDTO(
                "Arthur",
                100,
                10
        );
        farmer = Farmer.builder()
                .id(1L)
                .name("John")
                .balance(100)
                .farmLimit(10)
                .build();

        doNothing().when(farmerService).addFarmer(any(Farmer.class));
        //willDoNothing().given(farmerService).addFarmer(any(Farmer.class));

        // action
        mockMvc.perform(post("/api/v1/farmer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(farmerDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Farmer successfully added"));

        // verify
        verify(farmerService, times(1)).addFarmer(any(Farmer.class));
        /*response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name",
                        is(farmer.getName())))
                .andExpect(jsonPath("$.balance",
                        is(farmer.getBalance())))
                .andExpect(jsonPath("$.farmLimit",
                        is(farmer.getFarmLimit())));

         */
    }

    @Test
    @Order(2)
    public void getFarmerTest() throws Exception{
        // given
        List<Farmer> farmerList = new ArrayList<>();
        farmerList.add(farmer);
        farmerList.add(Farmer.builder()
                .id(1L)
                .name("John")
                .balance(100)
                .farmLimit(10)
                .build()
        );
        BDDMockito.given(farmerService.getFarmers()).willReturn(farmerList);

        // when
        mockMvc.perform(get("/api/v1/farmer"))
                .andExpect(status().isOk());

        // then
        verify(farmerService, times(1)).getFarmers();
        /*
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(farmerList.size())));

         */

    }

    @Test
    @Order(3)
    public void getByIdFarmerTest() throws Exception{
        // precondition
        BDDMockito.given(farmerService.findFarmerById(farmer.getId())).willReturn(Optional.of(farmer));

        // action
        ResultActions response = mockMvc.perform(get("/api/v1/farmer/{id}", farmer.getId()));

        // verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(farmer.getName())))
                .andExpect(jsonPath("$.balance", is(farmer.getBalance())))
                .andExpect(jsonPath("$.farmLimit", is(farmer.getFarmLimit())));
    }

    @Test
    @Order(4)
    public void updateFarmerTest() throws Exception{
        // precondition
        BDDMockito.given(farmerService.findFarmerById(farmer.getId())).willReturn(Optional.of(farmer));

        farmer.setName("Arthur");
        farmer.setBalance(100);

        //BDDMockito.given(farmerService.updateFarmer(farmer.getId(), farmer)).willReturn("Farmer updated successfully.");

        // action
        ResultActions response = mockMvc.perform(put("/api/v1/farmer/delete/{id}", farmer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(farmer)));

        // verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(farmer.getName())))
                .andExpect(jsonPath("$.balance", is(farmer.getBalance())))
                .andExpect(jsonPath("$.farmLimit", is(farmer.getFarmLimit())));
    }

    @Test
    public void deleteFarmerTest() throws Exception{
        // precondition
        willDoNothing().given(farmerService).deleteFarmer(farmer.getId());

        // action
        ResultActions response = mockMvc.perform(delete("/api/v1/farmer/delete/{id}", farmer.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}