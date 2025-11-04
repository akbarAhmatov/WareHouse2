package com.example.WareHouse2.repository;

import com.example.WareHouse2.entity.Warehouse;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class WarehouseRepository {
    private final Map<Long, Warehouse> warehouses = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Warehouse> findAll() {
        return new ArrayList<>(warehouses.values());
    }

    public Optional<Warehouse> findById(Long id) {
        return Optional.ofNullable(warehouses.get(id));
    }

    public Warehouse save(Warehouse warehouse) {
        if (warehouse.getId() == null) {
            warehouse.setId(idGenerator.getAndIncrement());
        }
        warehouses.put(warehouse.getId(), warehouse);
        return warehouse;
    }

    public void deleteById(Long id) {
        warehouses.remove(id);
    }

    public long count() {
        return warehouses.size();
    }
}
