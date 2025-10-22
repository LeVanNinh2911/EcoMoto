package com.example.EcoMoto.dto.admin;

import java.math.BigDecimal;

public class AdminProductDto {
    private String name;
    private Long brandId;
    private BigDecimal price;
    private String description;

    public AdminProductDto() {
    }

    public AdminProductDto(String name, Long brandId, BigDecimal price, String description) {
        this.name = name;
        this.brandId = brandId;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
