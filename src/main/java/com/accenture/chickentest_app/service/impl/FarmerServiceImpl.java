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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        if (!farmerRepository.existsById(id)) {
            return Optional.empty();
        }
        return farmerRepository.findById(id);
    }

    @Override
    public boolean buy(String type, Long farmerId, List<Object> cattle) {

        // variable de return
        boolean result = false;
        double totalPrice = 0.0;

        // acumulo el precio total - handleo de tipo chicken
        if (Objects.equals(type, "chicken")) {
            for (Object c : cattle) {
                if(c instanceof Chicken) {
                    Chicken auxChicken = (Chicken) c;
                    totalPrice += auxChicken.getPrice();
                }
            }
        }

        // acumulo el precio total - handleo de tipo egg
        if (Objects.equals(type, "egg")) {
            for (Object c : cattle) {
                if(c instanceof Egg) {
                    Egg auxEgg = (Egg) c;
                    totalPrice += auxEgg.getPrice();
                }
            }
        }

        Farmer farmer = farmerRepository
                .findById(farmerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + farmerId));

        // no hay suficiente balance
        if (farmer.getBalance() < totalPrice) {
            result = false;
        // si la capacidad actual supera al límite (chequear si es necesario eliminar esto)
        } else if (farmer.getCattle() >= farmer.getFarmLimit()) {
            result = false;
        // chequeamos el type que recibimos - chicken o egg
        } else if (Objects.equals(type, "chicken")) {
            // capturamos las gallinas a agregar
            List<Chicken> chickenAux = new ArrayList<>();
            if(cattle != null && !cattle.isEmpty()) {
                for(Object cattleAux : cattle) {
                    if(cattleAux instanceof Chicken) {
                        Chicken chicken = (Chicken) cattleAux;
                        farmer.setChickenQuantity(farmer.getChickenQuantity() + 1);
                        farmer.setCattle(farmer.getCattle() + 1);
                        chickenAux.add(chicken);
                    }
                }
                farmer.setBalance(farmer.getBalance() - totalPrice);
                chickenRepository.saveAll(chickenAux);

                // se pudo comprar satisfactoriamente
                result = true;
            }
        }  else if (Objects.equals(type, "egg")) {
            // capturamos los huevos a agregar
            List<Egg> eggAux = new ArrayList<>();
            if(cattle != null && !cattle.isEmpty()) {
                for(Object cattleAux : cattle) {
                    if(cattleAux instanceof Egg) {
                        Egg egg = (Egg) cattleAux;
                        farmer.setChickenQuantity(farmer.getChickenQuantity() + 1);
                        farmer.setCattle(farmer.getCattle() + 1);
                        eggAux.add(egg);
                    }
                }
                farmer.setBalance(farmer.getBalance() - totalPrice);
                eggRepository.saveAll(eggAux);
                // se pudo comprar satisfactoriamente
                result = true;
            }
        }
        return result;
    }

    @Override
    public boolean sell(List<Long> ids, Long farmerId, String type) {

        double totalPrice = 0.0;

        for (Long id : ids) {
            if (Objects.equals(type, "chicken")) {
                totalPrice += chickenRepository.getById(id).getPrice();
            }
            if (Objects.equals(type, "egg")) {
                totalPrice += eggRepository.getById(id).getPrice();
            }
        }

        Farmer farmer = farmerRepository
                .findById(farmerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + farmerId));

        // resultado del método - true si pudo vender, false sino
        boolean result = false;
        // chequea si se tiene suficiente ganado para vender - mínimo: 10% de cattle total
        if (farmer.getCattle() > (farmer.getCattle() * 0.1)) {
            if (Objects.equals(type, "chicken")) {
                //actualiza balance
                farmer.setBalance(farmer.getBalance() + totalPrice);
                //actualiza cantidad de gallinas
                farmer.setChickenQuantity(ids.size() - farmer.getChickenQuantity());
                //actualiza cantidad ganado total
                farmer.setCattle(farmer.getCattle() - ids.size());
                //elimina las gallinas vendidas
                chickenRepository.deleteAllById(ids);
                result = true;
            }

            if (Objects.equals(type, "egg")) {
                //actualiza balance
                farmer.setBalance(farmer.getBalance() + totalPrice);
                //actualiza cantidad de huevos
                farmer.setEggQuantity(ids.size() - farmer.getEggQuantity());
                //actualiza cantidad ganado total
                farmer.setCattle(farmer.getCattle() - ids.size());
                //elimina los huevos vendidos
                eggRepository.deleteAllById(ids);
                result = true;
            }
        }

        return result;
    }

    @Override
    public String getReport(Long id, int daysToAdvance) {
        /*
        farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));
        // si llega acá es que existe el granjero
        Optional<Farmer> farmer = farmerRepository.findById(id);
        */

        Farmer farmer = farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));


        // variable para chequear si se han eliminado gallinas como excedente
        boolean discardedChickens = false;

        // corremos el método para avanzar días
        advanceDays(id, daysToAdvance);

        int excessToRemove = 0;
        // chequeamos si la capacidad se excede
        if(farmer.getCattle() >= farmer.getFarmLimit()) {
            // determino el excedente
            excessToRemove = farmer.getCattle() - farmer.getFarmLimit();
            // elimino tantas gallinas como tenga de excedente
            for (int i = 0; i < excessToRemove && !farmer.getChickens().isEmpty(); i++) {
                farmer.getChickens().remove(farmer.getChickens().size() - 1);
            }
            discardedChickens = true;
        }

        if(discardedChickens) {
            return "Report for farmer: " + farmer.getName() + ", farmer balance: "
                    + farmer.getBalance() + ", chicken count: "
                    + farmer.getChickenQuantity() + ", egg count: " + farmer.getEggQuantity()
                    + " ," + excessToRemove + " chickens were discarded, as the capacity of the farm was exceeded.";
        } else {
            return "Report for farmer: " + farmer.getName() + ", farmer balance: "
                    + farmer.getBalance() + ", chicken count: "
                    + farmer.getChickenQuantity() + ", egg count: " + farmer.getEggQuantity();
        }
    }

    public void advanceDays(Long id, int daysToAdvance) {
        /*
        farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));
        // si llega acá es que existe el granjero
        Optional<Farmer> farmer = farmerRepository.findById(id);
        */

        Farmer farmer = farmerRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id inválido" + id));

        // expired chicken handling
        for(Chicken chicken : farmer.getChickens()) {
            chicken.setDaysLived(chicken.getDaysLived()+daysToAdvance);
            if(chicken.getDaysLived() >= 15) {
                farmer.getChickens().remove(chicken);
                chickenRepository.delete(chicken);
            }
        }

        // egg into chicken transformation handling
        for(Egg egg : farmer.getEggs()) {
            if(egg.getDaysLived() >= 15) {
                Chicken newChicken = new Chicken();
                //newChicken.setFarmer(farmer);
                newChicken.setPrice(1);
                newChicken.setDaysLived(1);
                farmer.getChickens().add(newChicken);
                chickenRepository.save(newChicken);
                farmer.getEggs().remove(egg);
                eggRepository.delete(egg);
            }
        }
    }
}
