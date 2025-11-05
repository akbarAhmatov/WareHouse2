package com.example.WareHouse2.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double calculate(double operand1, double operand2, String operation) {
        switch (operation) {
            case "+":
                return add(operand1, operand2);
            case "-":
                return subtract(operand1, operand2);
            case "×":
            case "*":
                return multiply(operand1, operand2);
            case "÷":
            case "/":
                return divide(operand1, operand2);
            case "%":
                return percentage(operand1, operand2);
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
    }

    private double add(double a, double b) {
        return Math.addExact((long) a, (long) b);
    }

    private double subtract(double a, double b) {
        return Math.subtractExact((long) a, (long) b);
    }

    private double multiply(double a, double b) {
        return Math.multiplyExact((long) a, (long) b);
    }

    private double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    private double percentage(double a, double b) {
        // Calculate: a - (a * b / 100)
        double percentValue = Math.multiplyExact((long) a, (long) b) / 100.0;
        return a - percentValue;
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
