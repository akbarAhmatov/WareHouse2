package com.example.WareHouse2.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double calculate(double operand1, double operand2, String operation) {
        return switch (operation) {
            case "+" -> add(operand1, operand2);
            case "-" -> subtract(operand1, operand2);
            case "×", "*" -> multiply(operand1, operand2);
            case "÷", "/" -> divide(operand1, operand2);
            case "%" -> percentage(operand1, operand2);
            default -> throw new IllegalArgumentException("Invalid operation: " + operation);
        };
    }

    private double add(double a, double b) {
        return a + b;
    }

    private double subtract(double a, double b) {
        
        return a - b;
    }

    private double multiply(double a, double b) {
        return a * b;
    }

    private double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    private double percentage(double a, double b) {
   return a - b;
    }

    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public double squareRoot(double number) {
        if (number < 0) {
            throw new ArithmeticException("Cannot calculate square root of negative number");
        }
        return Math.sqrt(number);
    }

    public double absolute(double number) {
        return Math.abs(number);
    }

    public double round(double number, int decimalPlaces) {
        double scale = Math.pow(10, decimalPlaces);
        return Math.round(number * scale) / scale;
    }
}