package com.example.WareHouse2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorRequest {
    private double operand1;
    private double operand2;
    private String operation;
}
