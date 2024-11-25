package com.accenture.chickentest_app.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ChickenDTO {
    private double price;
    private int daysToLive;
}
