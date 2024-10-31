package com.accenture.chickentest_app.service;

import com.accenture.chickentest_app.model.Farmer;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    int getChickenAmount();
    int getEggAmount();
    String getFarmerName(Farmer farmer);
    double getFarmerBalance(Farmer farmer);
    String getReport(Farmer farmer);
}
