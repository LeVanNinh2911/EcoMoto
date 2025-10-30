package com.example.EcoMoto.dto.admin;


import jakarta.persistence.Column;

public class AdminBrandDto {
    private String name;
    private String country;
    private String description;

    public AdminBrandDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

