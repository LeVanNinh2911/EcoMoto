package com.example.EcoMoto.controller;

import com.example.EcoMoto.entity.Brand;
import com.example.EcoMoto.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class BrandController {
    @Autowired
    private BrandRepository brandRepository;
    @GetMapping
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }
}
