package com.example.EcoMoto.dto.order;

import java.util.List;

public class GuestOrderRequestDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String paymentMethod;
    private List<OrderItemDto> items;

    public GuestOrderRequestDto() {
    }

    public GuestOrderRequestDto(String name, String email, String phone, String address, String paymentMethod, List<OrderItemDto> items) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.items = items;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
