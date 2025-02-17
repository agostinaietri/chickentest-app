package com.accenture.chickentest_app.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class FarmerDTO {

    @NotNull(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Balance cannot be blank")
    @Min(value = 1, message = "Balance should be greater than $1")
    @Max(value = 100, message = "Balance should be less than $100")
    private double balance;

    @NotNull(message = "Farm limit cannot be blank")
    @Min(value = 1, message = "Farm limit should be greater than 5")
    @Max(value = 50, message = "Farm limit value should be less than 50")
    private int farmLimit;
}
