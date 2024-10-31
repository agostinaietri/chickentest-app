package com.accenture.chickentest_app.service.impl;

import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.repository.ChickenRepository;
import com.accenture.chickentest_app.repository.EggRepository;
import com.accenture.chickentest_app.repository.FarmerRepository;
import com.accenture.chickentest_app.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private FarmerRepository farmerRepository;
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

    @Override
    public String getFarmerName(Farmer farmer) {
        return farmer.getName();
    }

    @Override
    public double getFarmerBalance(Farmer farmer) {
        return farmer.getBalance();
    }

    // make service method that looks for chicken and egg that are
    // ready to transform/die and include them in the report (returning
    // the id for them)

    @Override
    public String getReport(Farmer farmer) {
        return "Report for farmer: " + getFarmerName(farmer) + ", farmer balance: "
                + getFarmerBalance(farmer) + ", chicken count: "
                + getChickenAmount() + ", egg count: " + getEggAmount();
    }
}
