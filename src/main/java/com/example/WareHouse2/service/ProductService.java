package com.example.WareHouse2.service;

import com.example.WareHouse2.entity.Product;
import com.example.WareHouse2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final WarehouseService warehouseService;

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        products.forEach(this::enrichProductWithNames);
        return products;
    }

    public Optional<Product> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(this::enrichProductWithNames);
        return product;
    }

    public void save(Product product) {
        Product saved = productRepository.save(product);
        enrichProductWithNames(saved);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public long count() {
        return productRepository.count();
    }

    private void enrichProductWithNames(Product product) {
        if (product.getCategoryId() != null) {
            categoryService.findById(product.getCategoryId())
                    .ifPresent(category -> product.setCategoryName(category.getName()));
        }
        if (product.getWarehouseId() != null) {
            warehouseService.findById(product.getWarehouseId())
                    .ifPresent(warehouse -> product.setWarehouseName(warehouse.getName()));
        }
    }
}
