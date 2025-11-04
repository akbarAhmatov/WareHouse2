package com.example.WareHouse2.controller;

import com.example.WareHouse2.entity.Warehouse;
import com.example.WareHouse2.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    public String listWarehouses(Model model) {
        model.addAttribute("activeTab", "warehouses");
        model.addAttribute("warehouses", warehouseService.findAll());
        model.addAttribute("warehouse", new Warehouse());
        model.addAttribute("warehouseCount", warehouseService.count());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editWarehouse(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return warehouseService.findById(id)
                .map(warehouse -> {
                    model.addAttribute("activeTab", "warehouses");
                    model.addAttribute("warehouses", warehouseService.findAll());
                    model.addAttribute("warehouse", warehouse);
                    model.addAttribute("warehouseCount", warehouseService.count());
                    return "index";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Warehouse not found!");
                    return "redirect:/warehouses";
                });
    }

    @PostMapping("/save")
    public String saveWarehouse(@ModelAttribute Warehouse warehouse, RedirectAttributes redirectAttributes) {
        try {
            warehouseService.save(warehouse);
            redirectAttributes.addFlashAttribute("successMessage", 
                warehouse.getId() == null ? "Warehouse added successfully!" : "Warehouse updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving warehouse: " + e.getMessage());
        }
        return "redirect:/warehouses";
    }

    @PostMapping("/delete/{id}")
    public String deleteWarehouse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            warehouseService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Warehouse deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting warehouse: " + e.getMessage());
        }
        return "redirect:/warehouses";
    }
}
