package com.accenture.chickentest_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="chickens")
public class Chicken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="price", nullable = false)
    double price;
    @Column(name="days_life", nullable = false)
    int daysLived;
    @Column(name="total_days", nullable = false)
    final int totalDays = 15;

    private boolean lifeStatus = true;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Farmer farmer;

    public boolean getLifeStatus() {
        return lifeStatus;
    }
}
