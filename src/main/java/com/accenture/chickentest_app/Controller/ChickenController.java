package com.accenture.chickentest_app.Controller;

import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.service.ChickenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/chicken")
public class ChickenController {

    @Autowired
    private ChickenService chickenService;

    @PostMapping("/add")
    public String addChicken(@RequestBody Chicken chicken) {
        chickenService.addChicken(chicken);
        return "Chicken successfully added";
    }

    @GetMapping
    public List<Chicken> getChickens() {
        return chickenService.getChickens();
    }

    @GetMapping("/get")
    public Optional<Chicken> getChicken(@RequestParam Long id) {
        return chickenService.getChicken(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateChicken(@PathVariable Long id, @RequestBody Chicken chicken) {
        chickenService.updateChicken(id, chicken);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChicken(@PathVariable Long id) {
        chickenService.deleteChicken(id);
        return ResponseEntity.noContent().build();
    }

}
