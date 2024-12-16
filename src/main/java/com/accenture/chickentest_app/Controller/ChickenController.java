package com.accenture.chickentest_app.Controller;

import com.accenture.chickentest_app.dto.ChickenDTO;
import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.service.ChickenService;
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
@RequestMapping(path = "api/v1/chicken")
public class ChickenController {

    @Autowired
    private ChickenService chickenService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    public ResponseEntity<String> addChicken(@Valid @RequestBody ChickenDTO chickenDto) {
        Chicken chickenRequest = modelMapper.map(chickenDto, Chicken.class);

        if(chickenRequest.getDaysLived() >= 15) {
            return new ResponseEntity<>("The days lived for this Chicken exceed the total lifespan of a Chicken, as Chicken only live 15 days.", HttpStatus.BAD_REQUEST);
        }
        if(chickenRequest.getPrice() >= 15) {
            return new ResponseEntity<>("The Price exceeds the maximum price for Chickens, which is 15", HttpStatus.BAD_REQUEST);
        }
        //debería retornar el chicken que añadí?
        //ChickenDTO chickenResponse = modelMapper.map(chickenDto, ChickenDTO.class);

        chickenService.addChicken(chickenRequest);
        return ResponseEntity.ok("Chicken added successfully.");
    }

    @GetMapping
    public List<ChickenDTO> getChickens() {
        return chickenService.getChickens().stream().map(chicken -> modelMapper.map(chicken, ChickenDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/get")
    public ResponseEntity<ChickenDTO> getChicken(@RequestParam Long id) {
        Optional<Chicken> chicken = chickenService.getChicken(id);
        ChickenDTO chickenResponse = modelMapper.map(chicken, ChickenDTO.class);
        return ResponseEntity.ok().body(chickenResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateChicken(@PathVariable Long id, @Valid @RequestBody ChickenDTO chickenDTO) {
        /*
        Chicken chickenRequest = modelMapper.map(chickenDTO, Chicken.class);
        Chicken chicken = chickenService.updateChicken(id, chickenRequest);
        ChickenDTO chickenResponse = modelMapper.map(chicken, ChickenDTO.class);
        return ResponseEntity.ok().body(chickenResponse);
        */
        if(chickenService.findChickenById(id).isEmpty()) {
            return new ResponseEntity<>("No chicken with entered id were found.", HttpStatus.BAD_REQUEST);
        }
        Chicken chickenRequest = modelMapper.map(chickenDTO, Chicken.class);

        if(chickenRequest.getDaysLived() >= 15) {
            return new ResponseEntity<>("The days lived for this Chicken exceed the total lifespan of a Chicken, as Chicken only live 15 days.", HttpStatus.BAD_REQUEST);
        }
        if(chickenRequest.getPrice() >= 15) {
            return new ResponseEntity<>("The Price exceeds the maximum price for Chickens, which is 15", HttpStatus.BAD_REQUEST);
        }
        //ChickenDTO chickenResponse = modelMapper.map(chickenDto, ChickenDTO.class);

        chickenService.addChicken(chickenRequest);
        return ResponseEntity.ok("Chicken added successfully.");

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChicken(@PathVariable Long id) {
        chickenService.deleteChicken(id);
        return ResponseEntity.noContent().build();
    }

}
