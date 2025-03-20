package com.accenture.chickentest_app.model;

import com.accenture.chickentest_app.repository.FarmerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class FarmerTest {

    private final FarmerRepository farmerRepository;

    FarmerTest(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @Test
    void testFarmerName() {
        Farmer farmer = new Farmer();
        farmer.setName("John");

        String expected = "John";
        String actual = farmer.getName();
    }

    @Test
    void testFarmerGetById() {
        Farmer farmer = new Farmer();

    }
}