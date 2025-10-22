package com.example.EcoMoto.entity;

import com.example.EcoMoto.entity.enums.DiscountType;
import com.example.EcoMoto.entity.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    @JsonIgnoreProperties("products")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("product")
    private List<ProductColor> colors;

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

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    private Category category;

    // ================= THÊM PHẦN KHUYẾN MÃI =================

    @ManyToMany
    @JoinTable(
            name = "product_promotions",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    @JsonIgnoreProperties("products")
    private List<Promotion> promotions;

    // ✅ Tính giá sau khuyến mãi (finalPrice)
    @Transient
    public BigDecimal getFinalPrice() {
        if (promotions == null || promotions.isEmpty()) {
            return this.price;
        }

        LocalDate now = LocalDate.now();
        BigDecimal finalPrice = this.price;

        for (Promotion promo : promotions) {
            if (promo.isActive()
                    && promo.getStartDate() != null
                    && promo.getEndDate() != null
                    && (now.isEqual(promo.getStartDate()) || now.isAfter(promo.getStartDate()))
                    && (now.isEqual(promo.getEndDate()) || now.isBefore(promo.getEndDate()))) {

                if (promo.getDiscountType() == DiscountType.PERCENT) {
                    BigDecimal discount = price.multiply(promo.getDiscountValue().divide(BigDecimal.valueOf(100)));
                    finalPrice = finalPrice.subtract(discount);
                } else {
                    finalPrice = finalPrice.subtract(promo.getDiscountValue());
                }
            }
        }

        return finalPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : finalPrice;
    }

    // ================= CONSTRUCTORS =================

    public Product() {
    }

    public Product(Long id, String name, Brand brand, List<ProductColor> colors, BigDecimal price,
                   Integer rangePerCharge, Integer batteryCapacity, Integer maxSpeed, BigDecimal weight,
                   String imageUrl, String description, Integer stockQuantity, ProductStatus status,
                   Category category, List<Promotion> promotions) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.colors = colors;
        this.price = price;
        this.rangePerCharge = rangePerCharge;
        this.batteryCapacity = batteryCapacity;
        this.maxSpeed = maxSpeed;
        this.weight = weight;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.status = status;
        this.category = category;
        this.promotions = promotions;
    }

    // ================= GETTERS / SETTERS =================

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

    public List<ProductColor> getColors() {
        return colors;
    }

    public void setColors(List<ProductColor> colors) {
        this.colors = colors;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }
}
