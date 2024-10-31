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
@Table(name="chickens")
public class Chicken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="price", nullable = false)
    double price;
    // tentative
    @Transient
    int daysToLive;
}
