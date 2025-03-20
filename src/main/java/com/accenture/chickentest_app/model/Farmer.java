package com.accenture.chickentest_app.model;

import com.accenture.chickentest_app.dto.FarmerDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name="FARMERS")
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="name", nullable = false)
    String name;
    @Column(name="balance", nullable = false)
    double balance;
    @Column(name="cattle", nullable = false)
    int cattle;
    @Column(name="chicken_quantity", nullable = false)
    int chickenQuantity;
    @Column(name="egg_quantity", nullable = false)
    int eggQuantity;
    @Column(name="farm_limit")
    int farmLimit;

    @OneToMany(mappedBy="farmer", cascade = CascadeType.ALL)
    private List<Chicken> chickens;
    @OneToMany(mappedBy="farmer", cascade = CascadeType.ALL)
    private List<Egg> eggs;
}
