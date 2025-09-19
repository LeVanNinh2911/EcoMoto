package com.example.EcoMoto.dto.vnpay;

import lombok.Data;

@Data
public class VnPayRequest {
    private Long amount;       // Số tiền (VND)
    private String orderInfo;  // Mô tả đơn hàng
    private String orderId;// Mã đơn hàng

    public VnPayRequest() {
    }

    public VnPayRequest(Long amount, String orderInfo, String orderId) {
        this.amount = amount;
        this.orderInfo = orderInfo;
        this.orderId = orderId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}

