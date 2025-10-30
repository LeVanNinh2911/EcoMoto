package com.example.EcoMoto.repository;

import com.example.EcoMoto.entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor , Long> {
}
