package com.accenture.chickentest_app.service;

import com.accenture.chickentest_app.dto.FarmerDTO;
import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.repository.ChickenRepository;
import com.accenture.chickentest_app.repository.EggRepository;
import com.accenture.chickentest_app.repository.FarmerRepository;
import com.accenture.chickentest_app.service.impl.FarmerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import static org.assertj.core.api.Assertions.assertThat;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@WebMvcTest(FarmerService.class)
@ExtendWith(MockitoExtension.class)
public class FarmerServiceTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    //@MockBean
    @InjectMocks
    private FarmerServiceImpl farmerService;
    @MockBean
    private EggRepository eggRepository;
    @MockBean
    private FarmerRepository farmerRepository;
    @MockBean
    private ChickenRepository chickenRepository;
    @Mock
    private FarmerServiceImpl farmerServiceImpl;
    //@InjectMocks
    //private FarmerServiceImpl employeeService;

    private Farmer farmer;
    private Farmer farmer2;
    @Autowired
    private ModelMapper modelMapper;


    @BeforeEach
    public void setup(){

        Farmer farmer = Farmer.builder()
                .id(2L)
                .name("Arthur")
                .balance(100)
                .farmLimit(10)
                .build();

        farmer2 = new Farmer();
        farmer2.setName("Arthur");
        farmer2.setBalance(100);
        farmer2.setFarmLimit(10);

    }

    @Test
    public void givenFarmerObject_whenSaveFarmer_thenReturnFarmerObject(){
        Long farmerId = 1L;
        new Farmer();
        Farmer farmer1;
        farmer1 = new Farmer();
        farmer1.setId(farmerId);
        farmer1.setName("Arthur");
        farmer1.setBalance(100);
        farmer1.setFarmLimit(10);

        // given - precondition or setup
        doNothing().when(farmerService).addFarmer(any(Farmer.class));

        System.out.println(farmerRepository);
        System.out.println(farmerService);

        // when -  action or the behaviour that we are going test
        Boolean savedFarmer = farmerService.saveFarmer(farmer1);

        System.out.println(savedFarmer);
        // then - verify the output
        verify(farmerService, times(1)).saveFarmer(any(Farmer.class));
    }

    @Test
    @Order(2)
    public void getFarmerByIdTest(){
        // precondition
        Long farmerId = 1L;
        new Farmer();
        Farmer farmer1;
        farmer1 = new Farmer();
        farmer1.setId(farmerId);
        farmer1.setName("Arthur");
        farmer1.setBalance(100);
        farmer1.setFarmLimit(10);
        given(farmerRepository.findById(1L)).willReturn(Optional.of(farmer1));

        // action
        Optional<Farmer> existingFarmer = farmerService.findFarmerById(farmer1.getId());

        // verify
        System.out.println(existingFarmer);
        assertThat(existingFarmer).isNotNull();

    }

    @Test
    @Order(3)
    public void getAllFarmersTest(){
        List<Farmer> farmerList = new ArrayList<>();
        Long farmerId = 1L;
        new Farmer();
        Farmer farmer1;
        farmer1 = new Farmer();
        farmer1.setId(farmerId);
        farmer1.setName("Arthur");
        farmer1.setBalance(100);
        farmer1.setFarmLimit(10);
        farmerList.add(farmer1);

        when(farmerRepository.findAll()).thenReturn(farmerList);

        List<Farmer> farmers = farmerService.getFarmers();
        assertThat(!farmers.isEmpty());
        assertThat(farmerService.getFarmers()).isNotNull();

        verify(farmerService, times(2)).getFarmers();
    }

    @Test
    void farmerUpdateTest() {
        // Arrange
        Farmer savedFarmer = new Farmer();
        savedFarmer.setId(1L);
        savedFarmer.setName("John");
        Farmer updatedFarmer = new Farmer();
        updatedFarmer.setId(2L);
        updatedFarmer.setName("Arthur");

        when(farmerRepository.findById(savedFarmer.getId())).thenReturn(Optional.of(savedFarmer));
        when(farmerRepository.save(any(Farmer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        System.out.println("Mock findbyid: " + farmerRepository.findById(savedFarmer.getId()));
        System.out.println("Mock save: " + farmerRepository.save(savedFarmer));
        Farmer result2 = farmerService.updateFarmer(anyLong(), any(Farmer.class));
        System.out.println("Result: " + result2);

        //when


        //then
        verify(farmerRepository).save(any(Farmer.class));
        Mockito.verify(this.farmerRepository, Mockito.times(1))
                .save(Mockito.any(Farmer.class));
    }

    @DisplayName("JUnit test for deleteFarmer method")
    @Test
    public void givenFarmerId_whenDeleteFarmer_thenNothing(){
        // given - precondition or setup
        Farmer existingFarmer = new Farmer();
        existingFarmer.setId(1L);

        when(farmerRepository.findById(anyLong())).thenReturn(Optional.of(existingFarmer));
        System.out.println("Mock findbyid: " + farmerRepository.findById(1L));

        // when -  action or the behaviour that we are going test
        farmerService.deleteFarmer(1L);

        // then - verify the output
        verify(farmerRepository).delete(existingFarmer);
    }

    @Test
    public void sellChickenTest() {
        List<Long> chickenIds = new ArrayList<>();

        Farmer existingFarmer = new Farmer();
        existingFarmer.setId(1L);
        existingFarmer.setBalance(500);
        existingFarmer.setFarmLimit(15);
        existingFarmer.setCattle(10);
        existingFarmer.setChickenQuantity(10);

        when(farmerRepository.findById(1L)).thenReturn(Optional.of(existingFarmer));
        System.out.println("Mock findbyid: " + farmerRepository.findById(1L));

        //when
        Boolean result = farmerService.sellChicken(chickenIds, 1L);

        //then
        verify(chickenRepository).deleteAllById(chickenIds);

    }

    @Test
    public void buyChickenTest() {
        //given
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
        existingFarmer.setChickenQuantity(2);

        when(farmerRepository.findById(1L)).thenReturn(Optional.of(existingFarmer));
        System.out.println("Mock findbyid: " + farmerRepository.findById(1L));

        //when

        Boolean result = farmerService.buyChicken(chickens, 1L);

        //then
        assertTrue(result);
        verify(chickenRepository).saveAll(chickens);
        verify(farmerRepository).save(any(Farmer.class));
    }

}
