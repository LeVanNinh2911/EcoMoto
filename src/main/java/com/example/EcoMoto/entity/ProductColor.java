package com.example.EcoMoto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "product_colors")
public class ProductColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;  // tên màu, ví dụ "Red"

    @Column(name = "image_url", length = 255)
    private String imageUrl; // đường dẫn hình ảnh tương ứng

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("colors")
    private Product product;

    // ===== Constructors =====
    public ProductColor() {}

    public ProductColor(String name, String imageUrl, Product product) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.product = product;
    }

    // ===== Getters & Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}

