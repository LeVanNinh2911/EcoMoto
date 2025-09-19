package com.example.EcoMoto.service.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface VnPayService {
    String createPaymentUrl(Long amount, String orderInfo, String orderId, HttpServletRequest request);
}

