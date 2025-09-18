package com.example.EcoMoto.controller;

import com.example.EcoMoto.dto.order.GuestOrderRequestDto;
import com.example.EcoMoto.dto.order.OrderRequestDto;
import com.example.EcoMoto.dto.order.OrderResponseDto;
import com.example.EcoMoto.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Đặt hàng (có userId để biết ai mua)
    @PostMapping("/place/{userId}")
    public OrderResponseDto placeOrder(
            @PathVariable Long userId,
            @RequestBody OrderRequestDto request) {
        return orderService.placeOrder(userId, request);
    }
    @PostMapping("/place/guest")
    public OrderResponseDto placeGuestOrder(
            @RequestBody GuestOrderRequestDto request) {
        return orderService.placeGuestOrder(request);
    }
}

