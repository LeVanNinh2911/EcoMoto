package com.example.EcoMoto.controller.admin;

import com.example.EcoMoto.dto.admin.AdminProductDto;
import com.example.EcoMoto.entity.Product;
import com.example.EcoMoto.repository.ProductRepository;
import com.example.EcoMoto.service.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    @GetMapping
    public List<Product> getAllProducts() {
//        return productRepository.findAll().stream()
//                .map(p -> new ProductDTO(
//                        p.getId(),
//                        p.getName(),
//                        p.getPrice(),
//                        p.getImageUrl(),
//                        p.getDescription(),
//                        p.getCategory() != null ? p.getCategory().getName() : null,
//                        p.getBrand() != null ? p.getBrand().getName() : null
//                ))
//                .collect(Collectors.toList());
        return adminProductService.getAllProducts();
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody AdminProductDto dto) {
        return ResponseEntity.ok(adminProductService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody AdminProductDto dto) {
        return ResponseEntity.ok(adminProductService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        adminProductService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
