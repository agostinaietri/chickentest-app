package com.accenture.chickentest_app.service.impl;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Egg;
import com.accenture.chickentest_app.repository.ChickenRepository;
import com.accenture.chickentest_app.repository.EggRepository;
import com.accenture.chickentest_app.service.EggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EggServiceImpl implements EggService {

    @Autowired
    private EggRepository eggRepository;

    @Override
    public String addEgg(Egg egg) {
        eggRepository.save(egg);
        return "Egg was added successfully.";
    }

    @Override
    public List<Egg> getEggs() {
        return eggRepository.findAll();
    }

    @Override
    public Optional<Egg> getEgg(Long id) {
        Egg egg = eggRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id: " + id));
        return Optional.ofNullable(egg);
    }

    @Override
    public void updateEgg(Long id, Egg egg) {
        Egg existingEgg = eggRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id: " + id));
        existingEgg.setPrice(egg.getPrice());
        existingEgg.setDaysLived(egg.getDaysLived());
        eggRepository.save(existingEgg);
    }

    @Override
    public void deleteEgg(Long id) {
        Egg egg = eggRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id: " + id));

        eggRepository.delete(egg);
    }

    @Override
    public Optional<Egg> findEggById(Long id) {
        if(!eggRepository.existsById(id)) {
            return Optional.empty();
        }
        return eggRepository.findById(id);
    }
}
