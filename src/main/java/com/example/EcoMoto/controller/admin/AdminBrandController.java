package com.example.EcoMoto.controller.admin;

import com.example.EcoMoto.dto.admin.AdminBrandDto;
import com.example.EcoMoto.entity.Brand;
import com.example.EcoMoto.service.service.AdminBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/brands")
@PreAuthorize("hasRole('ADMIN')")
public class AdminBrandController {

    @Autowired
    private AdminBrandService adminBrandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(adminBrandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrand(@PathVariable Long id) {
        return ResponseEntity.ok(adminBrandService.getBrandById(id));
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody AdminBrandDto dto) {
        return ResponseEntity.ok(adminBrandService.createBrand(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id, @RequestBody AdminBrandDto dto) {
        return ResponseEntity.ok(adminBrandService.updateBrand(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        adminBrandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}

