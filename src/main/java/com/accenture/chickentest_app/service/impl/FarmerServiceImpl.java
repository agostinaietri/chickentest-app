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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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

    // for junit service test
    @Override
    public boolean saveFarmer(Farmer farmer) { farmerRepository.save(farmer) ; return true;}


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
    public Farmer updateFarmer(Long id, Farmer farmer) {
        Farmer existingFarmer = farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));

        existingFarmer.setBalance(farmer.getBalance());
        existingFarmer.setFarmLimit(farmer.getFarmLimit());

        farmerRepository.save(existingFarmer);
        return existingFarmer;
    }

    @Override
    public void deleteFarmer(Long id) {
        Farmer farmer = farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id: " + id));

        farmerRepository.delete(farmer);
    }

    @Override
    public Optional<Farmer> findFarmerById(Long id) {
        if (!farmerRepository.existsById(id)) {
            return Optional.empty();
        }
        return farmerRepository.findById(id);
    }

    @Override
    @Transactional
    public boolean buyChicken(List<Chicken> chicken, Long farmerId) {
        double totalPrice = 0.0;
        for (Chicken c : chicken) {
            System.out.println(c.getPrice());
            totalPrice += c.getPrice();
        }

        Farmer farmer = farmerRepository
                .findById(farmerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + farmerId));

        if(farmer.getChickens() == null) {
            farmer.setChickens(new ArrayList<>());
        }

        if (farmer.getBalance() < totalPrice) {
            return false;
        } else if (farmer.getCattle() >= farmer.getFarmLimit()) {
            return false;
        } else {
            //actualiza balance
            farmer.setBalance(farmer.getBalance() - totalPrice);
            //actualiza cantidad de gallinas
            farmer.setChickenQuantity(chicken.size() + farmer.getChickenQuantity());
            //actualiza cantidad de ganado total
            farmer.setCattle(farmer.getCattle() + chicken.size());
            for(Chicken c : chicken) {
                c.setFarmer(farmer);
                farmer.getChickens().add(c);
            }

            chickenRepository.saveAll(chicken);
            farmerRepository.save(farmer);
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
            farmer.setBalance(farmer.getBalance()+totalPrice);
            farmer.setChickenQuantity(farmer.getChickenQuantity() - chickenId.size());
            farmer.setCattle(farmer.getCattle() - chickenId.size());
            chickenRepository.deleteAllById(chickenId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean buyEgg(List<Egg> eggs, Long farmerId) {
        double totalPrice = 0.0;
        for (Egg e : eggs) {
            totalPrice += e.getPrice();
        }

        Farmer farmer = farmerRepository
                .findById(farmerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + farmerId));

        if (farmer.getBalance() < totalPrice) {
            return false;
        } else if (farmer.getCattle() >= farmer.getFarmLimit()) {
            return false;
        } else {
            //actualiza balance
            farmer.setBalance(farmer.getBalance() - totalPrice);
            //actualiza cantidad de huevos
            farmer.setEggQuantity(eggs.size() + farmer.getEggQuantity());
            //actualiza cantidad de ganado total
            farmer.setCattle(farmer.getCattle() + eggs.size());
            for(Egg e : eggs) {
                e.setFarmer(farmer);
                farmer.getEggs().add(e);
            }

            eggRepository.saveAll(eggs);
            farmerRepository.save(farmer);
            return true;
        }
    }

    @Override
    public boolean sellEgg(List<Long> eggsId, Long farmerId) {
        double totalPrice = 0.0;
        for(Long id : eggsId) {
            totalPrice += eggRepository.getById(id).getPrice();
        }

        Farmer farmer = farmerRepository
                .findById(farmerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + farmerId));

        //chequea si se tiene suficiente ganado para vender - mínimo: 10% de cattle total
        if(farmer.getCattle() > (farmer.getCattle() * 0.1)) {
            farmer.setBalance(farmer.getBalance()+totalPrice);
            farmer.setEggQuantity(farmer.getEggQuantity() - eggsId.size());
            farmer.setCattle(farmer.getCattle() - eggsId.size());
            eggRepository.deleteAllById(eggsId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getReport(Long id, int daysToAdvance) {

        Farmer farmer = farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));

        boolean discardedChickens = false;

        boolean changes = advanceDays(id, daysToAdvance);

        int excessToRemove = 0;
        if(farmer.getCattle() >= farmer.getFarmLimit()) {
            excessToRemove = farmer.getCattle() - farmer.getFarmLimit();
            for (int i = 0; i < excessToRemove && !farmer.getChickens().isEmpty(); i++) {
                farmer.getChickens().remove(farmer.getChickens().size() - 1);
                farmer.setChickenQuantity(farmer.getChickenQuantity() - excessToRemove);
                discardedChickens = true;
            }
        }

        ArrayList<Long> eggId = new ArrayList<>();
        for(Egg e : farmer.getEggs()) {
            eggId.add(e.getId());
        }
        ArrayList<Long> chickenId = new ArrayList<>();
        for(Chicken c : farmer.getChickens()) {
            chickenId.add(c.getId());
        }

        if(discardedChickens || changes) {
            return "Report for farmer: " + farmer.getName() + ", farmer balance: "
                    + farmer.getBalance() + ", chicken count: "
                    + farmer.getChickens().size() + " [chicken ids: (" + chickenId + ")], egg count: " + farmer.getEggs().size()
                    + " [egg ids: (" + eggId + ")], " + " farm limit: " + farmer.getFarmLimit()  + " chickens  were discarded, as the capacity of the farm was exceeded or the chicken expired.";
        } else {
            return "Report for farmer: " + farmer.getName() + ", farmer balance: "
                    + farmer.getBalance() + ", chicken count: "
                    + farmer.getChickens().size() + " [chicken ids: (" + chickenId + ")], egg count: " + farmer.getEggs().size() + " [egg ids: (" + eggId + ")], " + " farm limit: " + farmer.getFarmLimit() + ", " ;
        }
    }

    @Transactional
    public boolean advanceDays(Long id, int daysToAdvance) {

        boolean changes = false;

        Farmer farmer = farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id: " + id));

        // expired chicken handling
        List<Long> chickenRemoval = new ArrayList<>();
        for(Chicken chicken : farmer.getChickens()) {
            chicken.setDaysLived(chicken.getDaysLived() + daysToAdvance);
            if (chicken.getDaysLived() >= 15) {
                chickenRemoval.add(chicken.getId());
            }
        }

        if(!chickenRemoval.isEmpty()) {
            System.out.println("Chicken to be deleted: " + chickenRemoval);
            for(Long chickenId : chickenRemoval) {

                Chicken chickenToRemove = chickenRepository
                        .findById(chickenId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id: " + chickenId));

                chickenToRemove.setFarmer(null);
                chickenRepository.deleteById(chickenId);
                farmer.getChickens().remove(chickenToRemove);
                changes = true;
            }
        }

        // egg into chicken transformation handling
        List<Long> eggRemoval = new ArrayList<>();
        for(Egg egg : farmer.getEggs()) {

            // si está transformado lo skipeamos
            if(egg.isTransformed()) {
                continue;
            }

            egg.setDaysLived(egg.getDaysLived() + daysToAdvance);
            if(egg.getDaysLived() >= 15) {
                Chicken newChicken = new Chicken();
                newChicken.setFarmer(farmer);
                newChicken.setPrice(1);
                newChicken.setDaysLived(1);
                farmer.getChickens().add(newChicken);
                chickenRepository.save(newChicken);
                egg.setTransformed(true);
                eggRemoval.add(egg.getId());
            }
        }

        if(!eggRemoval.isEmpty()) {
            for(Long eggId : eggRemoval) {

                Egg eggToRemove = eggRepository
                        .findById(eggId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid id: " + eggId));

                eggToRemove.setFarmer(null);
                eggRepository.deleteById(eggId);
                farmer.getEggs().remove(eggToRemove);
                changes = true;
            }
        }
        //farmerRepository.flush();
        farmerRepository.save(farmer);

        return changes;
    }
}
