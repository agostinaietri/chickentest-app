package com.accenture.chickentest_app.service;

import com.accenture.chickentest_app.model.Egg;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EggService {

    void addEgg(Egg egg);

    List<Egg> getEggs();

    Optional<Egg> getEgg(Long id);

    void updateEgg(Long id, Egg egg);

    void deleteEgg(Long id);
}
