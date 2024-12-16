package com.accenture.chickentest_app.Controller;

import com.accenture.chickentest_app.dto.ChickenDTO;
import com.accenture.chickentest_app.dto.EggDTO;
import com.accenture.chickentest_app.dto.FarmerDTO;
import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.model.Egg;
import com.accenture.chickentest_app.model.Farmer;
import com.accenture.chickentest_app.service.EggService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/egg")
public class EggController {

    @Autowired
    private EggService eggService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<String> addEgg(@Valid @RequestBody EggDTO eggDto) {
        Egg eggRequest = modelMapper.map(eggDto, Egg.class);

        if(eggRequest.getDaysLived() >= 15) {
            return new ResponseEntity<>("The days lived for this Egg exceed the total lifespan of eggs, which is 15.", HttpStatus.BAD_REQUEST);
        }
        if(eggRequest.getPrice() >= 15) {
            return new ResponseEntity<>("The Price exceeds the maximum price for eggs, which is 15", HttpStatus.BAD_REQUEST);
        }

        eggService.addEgg(eggRequest);
        return ResponseEntity.ok("Egg added successfully.");
    }

    @GetMapping
    public List<EggDTO> getEggs() {
        //return eggService.getEggs();
        return eggService.getEggs().stream().map(egg -> modelMapper.map(egg, EggDTO.class))
                .collect(Collectors.toList());
    }

    /*
    @GetMapping("/get")
    public Optional<Egg> getEgg(@RequestParam Long id) {
        return eggService.getEgg(id);
    }
    */

    @GetMapping("/get")
    public ResponseEntity<Optional<EggDTO>> getFarmer(@RequestParam Long id) {
        if(eggService.findEggById(id).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Optional<Egg> egg = eggService.getEgg(id);
        EggDTO eggResponse = modelMapper.map(egg, EggDTO.class);
        return ResponseEntity.ok().body(Optional.ofNullable(eggResponse));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEgg(@PathVariable Long id, @Valid @RequestBody EggDTO eggDTO) {
        if(eggService.findEggById(id).isEmpty()) {
            return new ResponseEntity<>("No egg with entered id was found.", HttpStatus.BAD_REQUEST);
        }
        Egg eggRequest = modelMapper.map(eggDTO, Egg.class);
        eggService.addEgg(eggRequest);
        return ResponseEntity.ok("Egg added successfully.");

    }

    /*
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateEgg(@PathVariable Long id, @RequestBody Egg egg) {
        eggService.updateEgg(id, egg);
        return ResponseEntity.noContent().build();
    }
    */

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEgg(@PathVariable Long id) {
        if(eggService.findEggById(id).isEmpty()) {
            return new ResponseEntity<>("Egg with entered id not found", HttpStatus.BAD_REQUEST);
        }
        eggService.deleteEgg(id);
        return ResponseEntity.ok("Egg deleted successfully.");
    }
}
