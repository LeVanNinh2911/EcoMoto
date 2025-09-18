package com.example.EcoMoto.entity;

import com.example.EcoMoto.ProductStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(length = 50)
    private String color;

    @Column(precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "range_per_charge")
    private Integer rangePerCharge;

    @Column(name = "battery_capacity")
    private Integer batteryCapacity;

    @Column(name = "max_speed")
    private Integer maxSpeed;

    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "stock_quantity", columnDefinition = "int default 0")
    private Integer stockQuantity = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "enum('AVAILABLE','OUT_OF_STOCK','DISCONTINUED') default 'AVAILABLE'")
    private ProductStatus status = ProductStatus.AVAILABLE;

    public Product() {
    }

    public Product(Long id, String name, Brand brand, String color, BigDecimal price, Integer rangePerCharge, Integer batteryCapacity, Integer maxSpeed, BigDecimal weight, String imageUrl, String description, Integer stockQuantity, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.price = price;
        this.rangePerCharge = rangePerCharge;
        this.batteryCapacity = batteryCapacity;
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getRangePerCharge() {
        return rangePerCharge;
    }

    public void setRangePerCharge(Integer rangePerCharge) {
        this.rangePerCharge = rangePerCharge;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}

