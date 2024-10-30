package com.accenture.chickentest_app.service.impl;

import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.repository.FarmerRepository;
import com.accenture.chickentest_app.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FarmerServiceImpl implements FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;

    @Override
    public void addFarmer(Farmer farmer) {
        farmerRepository.save(farmer);
    }

    @Override
    public List<Farmer> getFarmers() {
        return farmerRepository.findAll();
    }

    @Override
    public Optional<Farmer> getFarmer(Long id) {
        Farmer farmer = farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));
        return Optional.ofNullable(farmer);
    }

    @Override
    public void updateFarmer(Long id, Farmer farmer) {
        farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));
        farmer.setId(id);
        farmerRepository.save(farmer);
    }

    @Override
    public void deleteFarmer(Long id) {
        Farmer farmer = farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));

        farmerRepository.delete(farmer);
    }
}
