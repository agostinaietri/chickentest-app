package com.accenture.chickentest_app.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Getter
@Setter
@Table(name="eggs")
public class Egg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="price", nullable = false)
    double price;
    // tentative
    @Column(name="days_turn", nullable = false)
    int daysToTurn;
}
