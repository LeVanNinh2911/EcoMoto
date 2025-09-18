package com.example.EcoMoto.dto.order;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

public class OrderResponseDto {
    private Long orderId;
    private BigDecimal totalAmount;
    private BigDecimal depositAmount;
    private String status;
    private String paymentUrl; // nếu chọn VNPAY thì trả link thanh toán giả lập
    private List<OrderItemDto> items;

    public OrderResponseDto() {
    }

    public OrderResponseDto(Long orderId, BigDecimal totalAmount, BigDecimal depositAmount, String status, String paymentUrl, List<OrderItemDto> items) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.depositAmount = depositAmount;
        this.status = status;
        this.paymentUrl = paymentUrl;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
