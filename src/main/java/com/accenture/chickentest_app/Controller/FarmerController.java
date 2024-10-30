package com.accenture.chickentest_app.Controller;

import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/farmer")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

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
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        farmerService.deleteFarmer(id);
        return ResponseEntity.noContent().build();
    }
}
