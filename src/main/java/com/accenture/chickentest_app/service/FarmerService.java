package com.accenture.chickentest_app.service;

import com.accenture.chickentest_app.model.Farmer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmerService {
    public List<Farmer> getFarmers() {
        return List.of(
                new Farmer(
                        1L,
                        "John",
                        10.15
                )
        );
    }
}
