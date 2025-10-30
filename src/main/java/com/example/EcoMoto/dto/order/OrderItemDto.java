package com.example.EcoMoto.dto.order;

import lombok.*;

public class OrderItemDto {
    private Long productId;
    private Long colorId ;
    private Integer quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(Long productId, Long colorId, Integer quantity) {
        this.productId = productId;
        this.colorId = colorId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

