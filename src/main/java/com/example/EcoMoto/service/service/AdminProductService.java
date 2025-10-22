package com.example.EcoMoto.service.service;

import com.example.EcoMoto.dto.admin.AdminProductDto;
import com.example.EcoMoto.entity.Product;

import java.util.List;

public interface AdminProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(AdminProductDto dto);
    Product updateProduct(Long id, AdminProductDto dto);
    void deleteProduct(Long id);
}

