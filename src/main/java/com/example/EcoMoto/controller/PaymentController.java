package com.example.EcoMoto.controller;



import com.example.EcoMoto.dto.vnpay.VnPayRequest;
import com.example.EcoMoto.service.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private VnPayService vnPayService;

    @PostMapping("/vnpay")
    public ResponseEntity<String> createPayment(@RequestBody VnPayRequest request, HttpServletRequest servletRequest) {
        String paymentUrl = vnPayService.createPaymentUrl(
                request.getAmount(),
                request.getOrderInfo(),
                request.getOrderId(),
                servletRequest
        );
        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<Map<String, String>> paymentReturn(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(params);
    }
}

