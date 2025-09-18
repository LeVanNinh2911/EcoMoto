package com.example.EcoMoto.repository;

import com.example.EcoMoto.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Có thể bổ sung query tùy theo nhu cầu
}

