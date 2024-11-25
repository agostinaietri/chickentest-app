package com.accenture.chickentest_app.service;

import com.accenture.chickentest_app.model.Chicken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChickenService {
    Chicken addChicken(Chicken chicken);

    List<Chicken> getChickens();

    Optional<Chicken> getChicken(Long id);

    Chicken updateChicken(Long id, Chicken chicken);

    void deleteChicken(Long id);
    void addAllChicken(List<Chicken> chicken);
    void advanceDays(int daysAdvanced);
    void removeDead();
    void dayChecker();
}
