package com.example.WareHouse2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorResponse {
    private double result;
    private String error;
    private boolean success;

    public static CalculatorResponse success(double result) {
        return new CalculatorResponse(result, null, true);
    }

    public static CalculatorResponse error(String error) {
        return new CalculatorResponse(0, error, false);
    }
}