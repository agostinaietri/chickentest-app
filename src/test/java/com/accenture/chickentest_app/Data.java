package com.accenture.chickentest_app;

import com.accenture.chickentest_app.dto.FarmerDTO;
import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Farmer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class Data {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private static ModelMapper modelMapper;

    FarmerDTO farmerDTO = new FarmerDTO("John", 100,20);
    public static Farmer Farmer01 = new Farmer();

    public static Optional<Farmer> createFarmer() {
        FarmerDTO farmerDTO = new FarmerDTO("John", 100,20);
        Farmer farmer1 = modelMapper.map(farmerDTO, Farmer.class);
        return Optional.of(farmer1);
    }


}