package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.dto.admin.AdminProductDto;
import com.example.EcoMoto.entity.Brand;
import com.example.EcoMoto.entity.Product;
import com.example.EcoMoto.repository.BrandRepository;
import com.example.EcoMoto.repository.ProductRepository;
import com.example.EcoMoto.service.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product createProduct(AdminProductDto dto) {
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(brand);
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, AdminProductDto dto) {
        Product product = getProductById(id);
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        product.setName(dto.getName());
        product.setBrand(brand);
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

