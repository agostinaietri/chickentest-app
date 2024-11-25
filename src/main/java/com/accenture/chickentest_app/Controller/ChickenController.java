package com.accenture.chickentest_app.Controller;

import com.accenture.chickentest_app.dto.ChickenDTO;
import com.accenture.chickentest_app.model.Chicken;
import com.accenture.chickentest_app.service.ChickenService;
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
    public ResponseEntity<ChickenDTO> addChicken(@RequestBody ChickenDTO chickenDto) {
        Chicken chickenRequest = modelMapper.map(chickenDto, Chicken.class);

        Chicken chicken = chickenService.addChicken(chickenRequest);

        ChickenDTO chickenResponse = modelMapper.map(chicken, ChickenDTO.class);

        chickenService.addChicken(chicken);
        return new ResponseEntity<ChickenDTO>(chickenResponse, HttpStatus.CREATED);
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
    public ResponseEntity<ChickenDTO> updateChicken(@PathVariable Long id, @RequestBody ChickenDTO chickenDTO) {
        Chicken chickenRequest = modelMapper.map(chickenDTO, Chicken.class);
        Chicken chicken = chickenService.updateChicken(id, chickenRequest);
        ChickenDTO chickenResponse = modelMapper.map(chicken, ChickenDTO.class);
        return ResponseEntity.ok().body(chickenResponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteChicken(@PathVariable Long id) {
        chickenService.deleteChicken(id);
        return ResponseEntity.noContent().build();
    }

}
