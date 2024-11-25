package com.accenture.chickentest_app.Controller;

import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.service.FarmerService;
import com.accenture.chickentest_app.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/report")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private FarmerService farmerService;

    /*
    @GetMapping("/report")
    public String getReport(@RequestParam Long id) {
        Optional<Farmer> farmer = farmerService.getFarmer(id);
        return reportService.getReport(farmer);
    }*/
}
