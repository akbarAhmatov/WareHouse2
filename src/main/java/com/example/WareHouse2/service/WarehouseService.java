package com.example.WareHouse2.service;

import com.example.WareHouse2.entity.Warehouse;
import com.example.WareHouse2.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;

    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    public void save(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
    }

    public void deleteById(Long id) {
        warehouseRepository.deleteById(id);
    }

    public long count() {
        return warehouseRepository.count();
    }
}
