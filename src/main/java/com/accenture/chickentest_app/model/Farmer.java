package com.accenture.chickentest_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Getter
@Setter
@Table(name="farmers")
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="name", nullable = false)
    String name;
    @Column(name="balance", nullable = false)
    double balance;
    // contiene cantidades de ambos gallinas y huevos - cantidad total granja
    @Column(name="cattle", nullable = false)
    int cattle;
    @Column(name="chicken_quantity", nullable = false)
    int chickenQuantity;
    @Column(name="egg_quantity", nullable = false)
    int eggQuantity;
    //límite total granja - no la cantidad actual (cattle)
    @Column(name="farm_limit")
    int farmLimit;

    @OneToMany(mappedBy="farmer", cascade = CascadeType.ALL)
    private List<Chicken> chickens;
    @OneToMany(mappedBy="farmer", cascade = CascadeType.ALL)
    private List<Egg> eggs;


}
