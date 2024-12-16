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
    @Transient
    int chickenQuantity;
    @Transient
    int eggQuantity;
    //límite total granja - no la cantidad actual (cattle)
    @Column(name="farm_limit")
    int farmLimit;

    @OneToMany(mappedBy="farmer", cascade = CascadeType.ALL)
    private List<Chicken> chickens = new ArrayList<>();
    @OneToMany(mappedBy="farmer", cascade = CascadeType.ALL)
    private List<Egg> eggs = new ArrayList<>();


}
