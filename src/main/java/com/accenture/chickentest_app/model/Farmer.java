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
@Table(name="farmers")
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="name", nullable = false)
    String name;
    @Column(name="balance", nullable = false)
    double balance;
    @Transient
    int farmCapacity;


}
