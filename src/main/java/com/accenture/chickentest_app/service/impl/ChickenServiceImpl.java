package com.accenture.chickentest_app.service.impl;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.repository.ChickenRepository;
import com.accenture.chickentest_app.repository.FarmerRepository;
import com.accenture.chickentest_app.service.ChickenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ChickenServiceImpl implements ChickenService {

    @Autowired
    private ChickenRepository chickenRepository;

    @Override
    public void addChicken(Chicken chicken) {
        chickenRepository.save(chicken);
    }

    @Override
    public List<Chicken> getChickens() {
        return chickenRepository.findAll();
    }

    @Override
    public Optional<Chicken> getChicken(Long id) {
        Chicken chicken = chickenRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));
        return Optional.ofNullable(chicken);
    }

    @Override
    public void updateChicken(Long id, Chicken chicken) {
        chickenRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));
        chicken.setId(id);
        chickenRepository.save(chicken);
    }

    @Override
    public void deleteChicken(Long id) {
        Chicken chicken = chickenRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));

        chickenRepository.delete(chicken);
    }
}
