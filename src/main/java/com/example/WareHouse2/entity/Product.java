package com.example.WareHouse2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 1000)
    private String description;

    // For display purposes (not stored in DB)
    @Transient
    private String categoryName;

    @Transient
    private String warehouseName;
}