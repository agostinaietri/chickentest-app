package com.accenture.chickentest_app.service.impl;

import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.repository.ChickenRepository;
import com.accenture.chickentest_app.repository.EggRepository;
import com.accenture.chickentest_app.service.FarmerService;
import com.accenture.chickentest_app.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private FarmerService farmerService;
    @Autowired
    private EggRepository eggRepository;
    @Autowired
    private ChickenRepository chickenRepository;

    @Override
    public int getChickenAmount() {
        return (int) chickenRepository.count();
    }

    @Override
    public int getEggAmount() {
        return (int) eggRepository.count();
    }

    /*
    @Override
    public String getFarmerName(Farmer farmer) {
        return farmer.getName();
    }

    @Override
    public double getFarmerBalance(Farmer farmer) {
        return farmer.getBalance();
    }
    */

    // make service method that looks for chicken and egg that are
    // ready to transform/die and include them in the report (returning
    // the id for them)

    // how would buying/selling work? just plainly calling the API method buy/sell?
    // or the add/delete method for chicken should be called via a buy/sell method in farmer?

    // then the farm capacity should be checked in order to keep it under limit
    // so if the limit is exceeded when buying, should the method for discarding/selling
    // be called?


    @Override
    public String getReport(Optional<Farmer> id) {
        Optional<Farmer> farmer = farmerService.getFarmer(id);
        return "Report for farmer: " + farmer.get().getName() + ", farmer balance: "
                + farmer.get().getBalance() + ", chicken count: "
                + getChickenAmount() + ", egg count: " + getEggAmount();
    }
}
