package com.example.WareHouse2;

import com.example.WareHouse2.dto.CalculatorRequest;
import com.example.WareHouse2.dto.CalculatorResponse;
import com.example.WareHouse2.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
@RequiredArgsConstructor
public class CalculatorRestController {
    
    private final CalculatorService calculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<CalculatorResponse> calculate(@RequestBody CalculatorRequest request) {
        try {
            double result = calculatorService.calculate(
                request.getOperand1(),
                request.getOperand2(),
                request.getOperation()
            );
            
            // Round to 8 decimal places to avoid floating point errors
            result = calculatorService.round(result, 8);
            
            return ResponseEntity.ok(CalculatorResponse.success(result));
        } catch (ArithmeticException e) {
            return ResponseEntity.ok(CalculatorResponse.error(e.getMessage()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(CalculatorResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body(CalculatorResponse.error("Calculation error: " + e.getMessage()));
        }
    }

    @PostMapping("/power")
    public ResponseEntity<CalculatorResponse> power(@RequestParam double base, @RequestParam double exponent) {
        try {
            double result = calculatorService.power(base, exponent);
            return ResponseEntity.ok(CalculatorResponse.success(result));
        } catch (Exception e) {
            return ResponseEntity.ok(CalculatorResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/sqrt")
    public ResponseEntity<CalculatorResponse> squareRoot(@RequestParam double number) {
        try {
            double result = calculatorService.squareRoot(number);
            return ResponseEntity.ok(CalculatorResponse.success(result));
        } catch (Exception e) {
            return ResponseEntity.ok(CalculatorResponse.error(e.getMessage()));
        }
    }
}
