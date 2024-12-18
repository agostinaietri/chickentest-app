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

    Optional<Farmer> findFarmerById(Long id);

    boolean buy(String type, Long farmerId, List<Object> cattle);
    boolean sell (List<Long> ids, Long farmerId, String type);

    String getReport(Long id, int daysToAdvance);

    void advanceDays(Long farmerId, int daysAdvanced);
    //void buyEgg(List<Egg> eggs);
    //void sellEggs(List<int> eggsId);
}
