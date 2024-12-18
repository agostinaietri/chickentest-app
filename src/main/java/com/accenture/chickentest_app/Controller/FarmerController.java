package com.accenture.chickentest_app.Controller;

import com.accenture.chickentest_app.dto.ChickenDTO;
import com.accenture.chickentest_app.dto.FarmerDTO;
import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.service.ChickenService;
import com.accenture.chickentest_app.service.FarmerService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "api/v1/farmer")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    public String addFarmer(@Valid @RequestBody FarmerDTO farmerDto) {
        Farmer farmerRequest = modelMapper.map(farmerDto, Farmer.class);
        farmerService.addFarmer(farmerRequest);
        return "Farmer successfully added";
    }

    @GetMapping
    public List<FarmerDTO> getFarmers() {
        return farmerService.getFarmers().stream().map(farmer -> modelMapper.map(farmer, FarmerDTO.class))
                .collect(Collectors.toList());
    }
    /*
    @GetMapping
    public List<Farmer> getFarmers() {
        return farmerService.getFarmers();
    }
    */

    /*
    @GetMapping("/get")
    public Optional<Farmer> getFarmer(@RequestParam Long id) {
        return farmerService.getFarmer(id);
    }
    */
    /*
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateFarmer(@PathVariable Long id, @RequestBody Farmer farmer) {
        farmerService.updateFarmer(id, farmer);
        return ResponseEntity.noContent().build();
    }*/

    @GetMapping("/get")
    public ResponseEntity<Optional<FarmerDTO>> getFarmer(@RequestParam Long id) {
        if(farmerService.findFarmerById(id).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Optional<Farmer> farmer = farmerService.getFarmer(id);
        FarmerDTO farmerResponse = modelMapper.map(farmer, FarmerDTO.class);
        return ResponseEntity.ok().body(Optional.ofNullable(farmerResponse));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFarmer(@PathVariable Long id, @Valid @RequestBody FarmerDTO farmerDTO) {
        if(farmerService.findFarmerById(id).isEmpty()) {
            return new ResponseEntity<>("No farmer with entered id was found.", HttpStatus.BAD_REQUEST);
        }
        Farmer farmerRequest = modelMapper.map(farmerDTO, Farmer.class);
        farmerService.addFarmer(farmerRequest);
        return ResponseEntity.ok("Farmer added successfully.");

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable Long id) {
        farmerService.deleteFarmer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/buy/{type}/{farmerId}")
    public ResponseEntity<String> buy(@PathVariable Long farmerId, @PathVariable String type, @Valid @RequestBody List<Object> cattle) {
        if(farmerService.buy(type, farmerId, cattle)) {
            return ResponseEntity.ok("Purchase was successful.");
        } else {
            return ResponseEntity.badRequest().body("Not enough balance to buy or farm capacity exceeded.");
        }
    }

    @PostMapping("/sell/{type}/{farmerId}")
    public ResponseEntity<String> sell(@PathVariable Long farmerId, @PathVariable String type, @Valid @RequestBody List<Long> ids) {
        if(farmerService.sell(ids, farmerId, type)) {
            return ResponseEntity.ok("Chicken sold successfully.");
        } else {
            return ResponseEntity.badRequest().body("Not enough cattle to be able to sell. Please check capacity.");
        }
    }

    @GetMapping("/report/{id}/{daysToAdvance}")
    public String getReport(@PathVariable Long id, @PathVariable int daysToAdvance) {
        return farmerService.getReport(id, daysToAdvance);
    }
}
