package com.example.EcoMoto.service.service;

import com.example.EcoMoto.dto.order.GuestOrderRequestDto;
import com.example.EcoMoto.dto.order.OrderRequestDto;
import com.example.EcoMoto.dto.order.OrderResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponseDto placeOrder(Long userId, OrderRequestDto request);
    OrderResponseDto placeGuestOrder(GuestOrderRequestDto request);
}
