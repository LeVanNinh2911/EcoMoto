package com.example.EcoMoto.service.service;

import com.example.EcoMoto.dto.admin.AdminBrandDto;
import com.example.EcoMoto.entity.Brand;

import java.util.List;

public interface AdminBrandService {
    List<Brand> getAllBrands();
    Brand getBrandById(Long id);
    Brand createBrand(AdminBrandDto dto);
    Brand updateBrand(Long id, AdminBrandDto dto);
    void deleteBrand(Long id);
}

