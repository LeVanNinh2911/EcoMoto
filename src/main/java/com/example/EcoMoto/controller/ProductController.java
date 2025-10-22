package com.example.EcoMoto.controller;

import com.example.EcoMoto.dto.ProductDTO;
import com.example.EcoMoto.entity.Product;
import com.example.EcoMoto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // ✅ Lấy danh sách sản phẩm (chỉ các thuộc tính cần thiết)
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
        return productRepository.findAll();
    }

    // ✅ Lọc theo brand
    @GetMapping("/by-brand/{brandId}")
    public List<ProductDTO> getProductsByBrand(@PathVariable Long brandId) {
        return productRepository.findByBrandId(brandId).stream()
                .map(p -> new ProductDTO(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getImageUrl(),
                        p.getDescription(),
                        p.getCategory() != null ? p.getCategory().getName() : null,
                        p.getBrand() != null ? p.getBrand().getName() : null
                ))
                .collect(Collectors.toList());
    }

    // ✅ Lấy chi tiết 1 sản phẩm (đầy đủ thông tin)
    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id);
    }
}



