package com.accenture.chickentest_app.Controller;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.service.ChickenService;
import com.accenture.chickentest_app.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "api/v1/farmer")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;
    @Autowired
    private ChickenService chickenService;

    @PostMapping("/add")
    public String addFarmer(@RequestBody Farmer farmer) {
        farmerService.addFarmer(farmer);
        return "Farmer successfully added";
    }

    @GetMapping
    public List<Farmer> getFarmers() {
        return farmerService.getFarmers();
    }

    @GetMapping("/get")
    public Optional<Farmer> getFarmer(@RequestParam Long id) {
        return farmerService.getFarmer(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateFarmer(@PathVariable Long id, @RequestBody Farmer farmer) {
        farmerService.updateFarmer(id, farmer);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable Long id) {
        farmerService.deleteFarmer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/buy/{farmerId}")
    public ResponseEntity<String> buyChicken(@PathVariable Long farmerId, @RequestBody List<Chicken> chicken) {
        if(farmerService.buyChicken(chicken, farmerId)) {
            return ResponseEntity.ok("Chicken purchased successfully.");
        } else {
            return ResponseEntity.badRequest().body("Not enough balance to buy.");
        }
    }

    @DeleteMapping("/delete/{farmerId}")
    public ResponseEntity<String> sellChicken(@PathVariable Long farmerId, @RequestBody List<Long> chicken) {
        farmerService.sellChicken(chicken, farmerId);
        return ResponseEntity.ok("Chicken sold successfully.");
    }
}
