package com.example.EcoMoto.controller;

import com.example.EcoMoto.entity.Product;
import com.example.EcoMoto.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/by-brand/{brandId}")
    public List<Product> getProductsByBrand(@PathVariable Long brandId) {
        return productRepository.findByBrandId(brandId);
    }
}

