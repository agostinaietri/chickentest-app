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

    @Transient //esta info es interna, es la que se actualiza con cada transacción
            //por eso es transient
            //chequear
            //conteo total de ganado
    int cattle;
    //no debería tener también un número máximo de gallinas?
    //eso se debe controlar cuando compran/venden
    @Transient
    int chickenQuantity;
    @Transient
    int eggQuantity;

    //mínimo de ganado - tal vez se puede handlear mejor
    @Column(name="min_quantity", nullable=false)
    int minFarmQuantity;

    //límite granja ganado
    @Column(name="farm_limit", nullable = false)
    int farmLimit;
}
