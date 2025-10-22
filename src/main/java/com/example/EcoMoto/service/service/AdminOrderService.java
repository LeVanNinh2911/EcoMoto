package com.example.EcoMoto.service.service;

import com.example.EcoMoto.entity.Order;

import java.util.List;

public interface AdminOrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order updateOrderStatus(Long id, String status); // PENDING, COMPLETED, CANCELLED
    void deleteOrder(Long id);
}
