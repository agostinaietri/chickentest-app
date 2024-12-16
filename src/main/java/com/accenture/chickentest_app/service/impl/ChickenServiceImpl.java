package com.accenture.chickentest_app.service.impl;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.repository.ChickenRepository;
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
    public String addChicken(Chicken chicken) {
        chickenRepository.save(chicken);
        return "Chicken was added successfully.";
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
    public Chicken updateChicken(Long id, Chicken chicken) {
        chickenRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));
        chicken.setId(id);
        chickenRepository.save(chicken);
        return null;
    }

    @Override
    public void deleteChicken(Long id) {
        Chicken chicken = chickenRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));

        chickenRepository.delete(chicken);
    }

    @Override
    public Optional<Chicken> findChickenById(Long id) {
        if(!chickenRepository.existsById(id)) {
            return Optional.empty();
        }
        return chickenRepository.findById(id);
    }

    @Override
    public void addAllChicken(List<Chicken> chicken) {
        chickenRepository.saveAll(chicken);
    }
    /*
    @Override
    public void advanceDays(Long farmerId, int daysAdvanced) {
        List<Chicken> chickenFromFarmer = farmer
        List<Chicken> allChicken = chickenRepository.findAll();
        for(Chicken chicken : allChicken) {
            chicken.setDaysLived(chicken.getDaysLived()+daysAdvanced);
        }
    }*/

    @Override
    public void removeDead() {
        List<Chicken> allChicken = chickenRepository.findAll();
        for(Chicken chicken : allChicken) {
            if(chicken.getLifeStatus()) {
                chickenRepository.delete(chicken);
            }
        }
    }

    @Override
    public void dayChecker() {
        List<Chicken> allChicken = chickenRepository.findAll();
        for(Chicken chicken : allChicken) {
            if(chicken.getDaysLived() >= chicken.getTotalDays()) {
                chicken.setLifeStatus(true);
            }
        }
    }
}
