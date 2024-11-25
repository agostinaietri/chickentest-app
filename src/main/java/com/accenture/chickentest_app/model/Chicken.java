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
    // estos días son los que le quedan de vida, se modificarán
    @Column(name="days_life", nullable = false)
    int daysLived;
    //estos días no se modifican, es el valor total de la vida
    //para todas las gallinas será de 15
    //se pide al crear una gallina
    @Column(name="total_days", nullable = false)
    final int totalDays = 15;

    //tentative
    //seteo de estado de gallina: lista a ser descartada
    //debería ser guardado en la db?
    //cuando se crea la gallina el estado es true
    private boolean lifeStatus = true;

    public boolean getLifeStatus() {
        return lifeStatus;
    }
}
