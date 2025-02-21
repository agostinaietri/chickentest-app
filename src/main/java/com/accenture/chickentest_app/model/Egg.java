package com.accenture.chickentest_app.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="EGGS")
public class Egg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="price", nullable = false)
    double price;

    int daysLived;

    @Column(name="transformed", nullable = false)
    boolean transformed;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Farmer farmer;
}
