package com.example.WareHouse2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String sku;
    private Long categoryId;
    private Long warehouseId;
    private Double price;
    private Integer quantity;
    private String description;
    
    // For display purposes
    private String categoryName;
    private String warehouseName;
}
