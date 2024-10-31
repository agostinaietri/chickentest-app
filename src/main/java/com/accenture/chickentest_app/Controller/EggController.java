package com.accenture.chickentest_app.Controller;

import com.accenture.chickentest_app.model.Egg;
import com.accenture.chickentest_app.service.EggService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/egg")
public class EggController {

    @Autowired
    private EggService eggService;

    @PostMapping("/add")
    public String addEgg(@RequestBody Egg egg) {
        eggService.addEgg(egg);
        return "Egg successfully added";
    }

    @GetMapping
    public List<Egg> getEggs() {
        return eggService.getEggs();
    }

    @GetMapping("/get")
    public Optional<Egg> getEgg(@RequestParam Long id) {
        return eggService.getEgg(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateEgg(@PathVariable Long id, @RequestBody Egg egg) {
        eggService.updateEgg(id, egg);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEgg(@PathVariable Long id) {
        eggService.deleteEgg(id);
        return ResponseEntity.noContent().build();
    }
}
