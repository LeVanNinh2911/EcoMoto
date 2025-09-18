package com.example.EcoMoto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "compare_history")
public class CompareHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // User thực hiện so sánh

    @ManyToMany
    @JoinTable(
            name = "compare_history_products",
            joinColumns = @JoinColumn(name = "compare_history_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;  // Danh sách sản phẩm so sánh

    private LocalDateTime comparedAt;

    public CompareHistory() {
    }

    public CompareHistory(Long id, User user, List<Product> products, LocalDateTime comparedAt) {
        this.id = id;
        this.user = user;
        this.products = products;
        this.comparedAt = comparedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getComparedAt() {
        return comparedAt;
    }

    public void setComparedAt(LocalDateTime comparedAt) {
        this.comparedAt = comparedAt;
    }
}
