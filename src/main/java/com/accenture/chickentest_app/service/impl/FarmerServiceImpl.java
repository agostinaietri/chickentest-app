package com.accenture.chickentest_app.service.impl;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Egg;
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
    public Optional<Farmer> findFarmerById(Long id) {
        if(!farmerRepository.existsById(id)) {
            return Optional.empty();
        }
        return farmerRepository.findById(id);
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
        } else if(farmer.getCattle() >= farmer.getFarmLimit()) {
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

        //chequea si se tiene suficiente ganado para vender - mínimo: 10% de cattle total
        if(farmer.getCattle() > (farmer.getCattle() * 0.1)) {
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

    @Override
    public String getReport(Long id, int daysToAdvance) {
        farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));
        // si llega acá es que existe el granjero
        Optional<Farmer> farmer = farmerRepository.findById(id);

        // corremos el método para avanzar días
        advanceDays(id, daysToAdvance);

        return "Report for farmer: " + farmer.get().getName() + ", farmer balance: "
                + farmer.get().getBalance() + ", chicken count: "
                + farmer.get().getChickenQuantity() + ", egg count: " + farmer.get().getEggQuantity();
    }

    public void advanceDays(Long id, int daysToAdvance) {
        farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));
        // si llega acá es que existe el granjero
        Optional<Farmer> farmer = farmerRepository.findById(id);

        // expired chicken handling
        for(Chicken chicken : farmer.get().getChickens()) {
            chicken.setDaysLived(chicken.getDaysLived()+daysToAdvance);
            if(chicken.getDaysLived() >= 15) {
                farmer.get().getChickens().remove(chicken);
                chickenRepository.delete(chicken);
            }
        }

        // egg into chicken transformation handling
        for(Egg egg : farmer.get().getEggs()) {
            if(egg.getDaysLived() >= 15) {
                Chicken newChicken = new Chicken();
                //newChicken.setFarmer(farmer);
                //newChicken.setPrice(1);
                newChicken.setDaysLived(1);
                farmer.get().getChickens().add(newChicken);
                chickenRepository.save(newChicken);
                farmer.get().getEggs().remove(egg);
                eggRepository.delete(egg);
            }
        }
    }
}
