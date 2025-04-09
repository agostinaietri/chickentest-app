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
    boolean saveFarmer(Farmer farmer);

    List<Farmer> getFarmers();

    Optional<Farmer> getFarmer(Long id);

    Farmer updateFarmer(Long id, Farmer farmer);

    void deleteFarmer(Long id);

    Optional<Farmer> findFarmerById(Long id);

    String getReport(Long id, int daysToAdvance);

    boolean advanceDays(Long farmerId, int daysAdvanced);

    boolean buyChicken(List<Chicken> chicken, Long farmerId);

    boolean sellChicken(List<Long> chickenId, Long farmerId);

    boolean buyEgg(List<Egg> egg, Long farmerId);

    boolean sellEgg(List<Long> eggId, Long farmerId);
}
