package com.accenture.chickentest_app.service;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Egg;
import com.accenture.chickentest_app.model.Farmer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FarmerService {

    void addFarmer(Farmer farmer);

    List<Farmer> getFarmers();

    Optional<Farmer> getFarmer(Long id);

    void updateFarmer(Long id, Farmer farmer);

    void deleteFarmer(Long id);

    boolean buyChicken(List<Chicken> chicken, Long farmerId);
    boolean sellChicken(List<Long> chickenId, Long farmerId);
    //void buyEgg(List<Egg> eggs);
    //void sellEggs(List<int> eggsId);
}
