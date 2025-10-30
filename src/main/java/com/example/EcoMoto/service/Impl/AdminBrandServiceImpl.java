package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.dto.admin.AdminBrandDto;
import com.example.EcoMoto.entity.Brand;
import com.example.EcoMoto.repository.BrandRepository;
import com.example.EcoMoto.service.service.AdminBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminBrandServiceImpl implements AdminBrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
    }

    @Override
    public Brand createBrand(AdminBrandDto dto) {
        Brand brand = new Brand();
        brand.setName(dto.getName());
        brand.setCountry(dto.getCountry());
        brand.setDescription(dto.getDescription());
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Long id, AdminBrandDto dto) {
        Brand brand = getBrandById(id);
        brand.setName(dto.getName());
        brand.setCountry(dto.getCountry());
        brand.setDescription(dto.getDescription());
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
}

