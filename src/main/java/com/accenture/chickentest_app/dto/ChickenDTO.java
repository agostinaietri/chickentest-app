package com.accenture.chickentest_app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ChickenDTO {
    /*
    private double price;
    private int daysToLive;
    */

    @NotNull(message = "Price cannot be blank")
    @Min(value = 1, message = "Price should be greater than $0.01")
    @Max(value = 100, message = "Price should be less than $15")
    private double price;

    @NotNull(message = "Days lived cannot be blank")
    @Min(value = 1, message = "Days lived should be greater than 1")
    @Max(value = 15, message = "Days lived should be less than 15")
    private int daysLived;
}
