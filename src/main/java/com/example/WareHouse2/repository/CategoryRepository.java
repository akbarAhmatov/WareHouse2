package com.example.WareHouse2.repository;

import com.example.WareHouse2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}