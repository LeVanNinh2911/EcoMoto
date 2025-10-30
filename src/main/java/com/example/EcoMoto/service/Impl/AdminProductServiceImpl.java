package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.dto.admin.AdminProductDto;
import com.example.EcoMoto.entity.*;
import com.example.EcoMoto.entity.enums.ProductStatus;
import com.example.EcoMoto.repository.BrandRepository;
import com.example.EcoMoto.repository.CategoryRepository;
import com.example.EcoMoto.repository.ProductRepository;
import com.example.EcoMoto.repository.PromotionRepository;
import com.example.EcoMoto.service.service.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PromotionRepository promotionRepository;

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
        // 1️⃣ Lấy brand và category
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // 2️⃣ Khởi tạo Product
        Product product = new Product();
        product.setName(dto.getName());
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(dto.getPrice());
        product.setRangePerCharge(dto.getRangePerCharge());
        product.setBatteryCapacity(dto.getBatteryCapacity());
        product.setMaxSpeed(dto.getMaxSpeed());
        product.setWeight(BigDecimal.valueOf(dto.getWeight()));
        product.setImageUrl(dto.getImageUrl());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStockQuantity());
        product.setStatus(ProductStatus.valueOf(dto.getStatus()));

        // 3️⃣ Gán danh sách khuyến mãi (nếu có)
        if (dto.getPromotionIds() != null && !dto.getPromotionIds().isEmpty()) {
            List<Promotion> promotions = promotionRepository.findAllById(dto.getPromotionIds());
            product.setPromotions(promotions);
        }

        // 4️⃣ Gán danh sách màu (nếu có)
        if (dto.getColors() != null && !dto.getColors().isEmpty()) {
            List<ProductColor> colors = dto.getColors().stream()
                    .map(colorDto -> {
                        ProductColor color = new ProductColor();
                        color.setName(colorDto.getName());
                        color.setImageUrl(colorDto.getImageUrl());
                        color.setProduct(product); // liên kết ngược
                        return color;
                    })
                    .collect(Collectors.toList());
            product.setColors(colors);
        }

        // 5️⃣ Lưu sản phẩm
        return productRepository.save(product);
    }


    @Override
    public Product updateProduct(Long id, AdminProductDto dto) {
        Product product = getProductById(id);

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(dto.getName());
        product.setBrand(brand);
        product.setCategory(category);
        product.setPrice(dto.getPrice());
        product.setRangePerCharge(dto.getRangePerCharge());
        product.setBatteryCapacity(dto.getBatteryCapacity());
        product.setMaxSpeed(dto.getMaxSpeed());
        product.setWeight(BigDecimal.valueOf(dto.getWeight()));
        product.setImageUrl(dto.getImageUrl());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStockQuantity());
        product.setStatus(ProductStatus.valueOf(dto.getStatus()));

        // Cập nhật khuyến mãi
        if (dto.getPromotionIds() != null && !dto.getPromotionIds().isEmpty()) {
            List<Promotion> promotions = promotionRepository.findAllById(dto.getPromotionIds());
            product.setPromotions(promotions);
        } else {
            product.setPromotions(new ArrayList<>()); // ✅ sửa ở đây
        }

// Cập nhật danh sách màu
        if (product.getColors() != null) {
            product.getColors().clear();
        } else {
            product.setColors(new ArrayList<>()); // ✅ thêm dòng này để tránh null
        }

        if (dto.getColors() != null && !dto.getColors().isEmpty()) {
            List<ProductColor> newColors = dto.getColors().stream()
                    .map(colorDto -> {
                        ProductColor color = new ProductColor();
                        color.setName(colorDto.getName());
                        color.setImageUrl(colorDto.getImageUrl());
                        color.setProduct(product);
                        return color;
                    })
                    .collect(Collectors.toList());
            product.getColors().addAll(newColors);
        }


        return productRepository.save(product);
    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

