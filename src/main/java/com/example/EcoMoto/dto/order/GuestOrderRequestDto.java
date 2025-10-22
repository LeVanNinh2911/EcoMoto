package com.example.EcoMoto.dto.order;

import com.example.EcoMoto.entity.enums.PaymentMethod;

import java.util.List;

public class GuestOrderRequestDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private PaymentMethod paymentMethod;
    private List<OrderItemDto> items;
    private Double installmentDownPaymentPercent; // phần trăm trả trước (VD: 0.2 = 20%)

    public GuestOrderRequestDto() {
    }

    public GuestOrderRequestDto(String name, String email, String phone, String address, PaymentMethod paymentMethod, List<OrderItemDto> items, Double installmentDownPaymentPercent) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.items = items;
        this.installmentDownPaymentPercent = installmentDownPaymentPercent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public Double getInstallmentDownPaymentPercent() {
        return installmentDownPaymentPercent;
    }

    public void setInstallmentDownPaymentPercent(Double installmentDownPaymentPercent) {
        this.installmentDownPaymentPercent = installmentDownPaymentPercent;
    }
}
