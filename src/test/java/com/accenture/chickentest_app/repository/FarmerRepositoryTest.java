package com.accenture.chickentest_app.repository;

import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.repository.FarmerRepository;
import com.accenture.chickentest_app.service.FarmerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FarmerRepositoryTest {

    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private FarmerService farmerService;

    private Farmer testFarmer;

    @BeforeEach
    public void setUp() {
        // Initialize test data before each test method
        testFarmer = new Farmer();
        testFarmer.setFarmLimit(10);
        testFarmer.setName("testFarmer");
        testFarmer.setBalance(100);
        farmerRepository.save(testFarmer);
    }

    @AfterEach
    public void tearDown() {
        // Release test data after each test method
        farmerRepository.delete(testFarmer);
    }

    @DisplayName("JUnit test for saveFarmer method")
    @Test
    @Order(1)
    void givenFarmer_whenSaved_thenCanBeFoundById() {
        Farmer savedFarmer = farmerRepository.findById(testFarmer.getId()).orElse(null);
        Assertions.assertNotNull(savedFarmer);
        assertEquals(testFarmer.getName(), savedFarmer.getName());
        assertEquals(testFarmer.getFarmLimit(), savedFarmer.getFarmLimit());
    }

    @DisplayName("JUnit test for getListOfFarmers method")
    @Test
    @Order(2)
    public void getListOfFarmersTest(){
        //Action
        List<Farmer> farmers = farmerRepository.findAll();
        //Verify
        System.out.println(farmers);
        assertThat(farmers.size()).isGreaterThan(0);
    }

    @DisplayName("JUnit test for updateFarmer method")
    @Test
    @Order(4)
    void givenFarmer_whenUpdated_thenCanBeFoundByIdWithUpdatedData() {
        testFarmer.setName("updatedFarmerName");
        farmerRepository.save(testFarmer);

        Farmer updatedFarmer = farmerRepository.findById(testFarmer.getId()).orElse(null);

        assertNotNull(updatedFarmer);
        assertEquals("updatedFarmerName", updatedFarmer.getName());
    }

    @Test
    @Order(5)
    public void deleteFarmerTest(){
        //Action
        farmerRepository.deleteById(1L);
        Optional<Farmer> farmerOptional = farmerRepository.findById(1L);

        //Verify
        assertThat(farmerOptional).isEmpty();
    }
}