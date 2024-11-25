package com.accenture.chickentest_app.service.impl;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.repository.ChickenRepository;
import com.accenture.chickentest_app.repository.EggRepository;
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
    @Autowired
    private ChickenRepository chickenRepository;
    @Autowired
    private EggRepository eggRepository;

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

    @Override
    public boolean buyChicken(List<Chicken> chicken, Long farmerId) {

        double totalPrice = 0.0;
        for(Chicken c : chicken) {
            totalPrice += c.getPrice();
        }

        Farmer farmer = farmerRepository
                .findById(farmerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + farmerId));

        if(farmer.getBalance() < totalPrice) {
            return false;
        } else {
            //actualiza balance
            farmer.setBalance(farmer.getBalance()-totalPrice);
            //actualiza cantidad de gallinas
            farmer.setChickenQuantity(chicken.size()+farmer.getChickenQuantity());
            //actualiza cantidad de ganado total
            farmer.setCattle(farmer.getCattle() + chicken.size());

            chickenRepository.saveAll(chicken);
            return true;
        }
    }

    @Override
    public boolean sellChicken(List<Long> chickenId, Long farmerId) {

        double totalPrice = 0.0;
        for(Long id : chickenId) {
            totalPrice += chickenRepository.getById(id).getPrice();
        }

        Farmer farmer = farmerRepository
                .findById(farmerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + farmerId));

        //chequea si se tiene suficiente ganado para vender
        if(farmer.getCattle() < farmer.getMinFarmQuantity()) {
            //actualiza balance
            farmer.setBalance(farmer.getBalance()+totalPrice);
            //actualiza cantidad de gallinas
            farmer.setChickenQuantity(chickenId.size()-farmer.getChickenQuantity());
            //actualiza cantidad ganado total
            farmer.setCattle(farmer.getCattle() - chickenId.size());
            //elimina las gallinas vendidas
            chickenRepository.deleteAllById(chickenId);

            return true;
        } else {
            return false;
        }
    }
}
