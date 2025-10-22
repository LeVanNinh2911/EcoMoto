package com.example.EcoMoto.controller;

import com.example.EcoMoto.dto.order.*;
import com.example.EcoMoto.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    // ======================================================
    // 🧑‍💻 Đặt hàng cho người dùng đã đăng nhập
    // ======================================================
    @PostMapping("/user/{userId}")
    public ResponseEntity<OrderResponseDto> placeOrder(
            @PathVariable Long userId,
            @RequestBody OrderRequestDto request
    ) {
        try {
            OrderResponseDto response = orderService.placeOrder(userId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new OrderResponseDto(null, null, null, "FAILED", null, null)
            );
        }
    }

    // ======================================================
    // 🚗 Đặt hàng cho khách chưa đăng nhập
    // ======================================================
    @PostMapping("/guest")
    public ResponseEntity<OrderResponseDto> placeGuestOrder(
            @RequestBody GuestOrderRequestDto request
    ) {
        try {
            OrderResponseDto response = orderService.placeGuestOrder(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new OrderResponseDto(null, null, null, "FAILED", null, null)
            );
        }
    }

    // ======================================================
    // 🧾 Xử lý phản hồi thanh toán (VD: từ VNPay callback)
    // ======================================================
    @GetMapping("/vnpay-return")
    public ResponseEntity<String> handleVnpayReturn(
            @RequestParam(name = "vnp_ResponseCode", required = false) String responseCode,
            @RequestParam(name = "vnp_TxnRef", required = false) String orderId
    ) {
        if ("00".equals(responseCode)) {
            // Giao dịch thành công
            return ResponseEntity.ok("Thanh toán thành công cho đơn hàng #" + orderId);
        } else {
            // Giao dịch thất bại hoặc bị hủy
            return ResponseEntity.badRequest().body("Thanh toán thất bại hoặc bị hủy.");
        }
    }

    // ======================================================
    // 🧮 Kiểm tra trạng thái đơn hàng (tuỳ chọn)
    // ======================================================
    @GetMapping("/{orderId}/status")
    public ResponseEntity<String> getOrderStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok("Tính năng kiểm tra trạng thái đang được phát triển...");
    }
}

