package com.accenture.chickentest_app.service;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Farmer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ChickenService {
    void addChicken(Chicken chicken);

    List<Chicken> getChickens();

    Optional<Chicken> getChicken(Long id);

    void updateChicken(Long id, Chicken chicken);

    void deleteChicken(Long id);
}
