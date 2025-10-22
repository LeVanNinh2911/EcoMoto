package com.example.EcoMoto.controller;

import com.example.EcoMoto.entity.enums.ExchangeStatus;
import com.example.EcoMoto.dto.exchange.ExchangeRequestDto;
import com.example.EcoMoto.entity.ExchangeRequest;
import com.example.EcoMoto.service.service.ExchangeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange-requests")
public class ExchangeRequestController {

    @Autowired
    private ExchangeRequestService exchangeRequestService;

    // 1. Tạo yêu cầu đổi xe
    @PostMapping
    public ResponseEntity<ExchangeRequest> createExchangeRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody ExchangeRequestDto dto) {

        // Xóa "Bearer " nếu token đầy đủ từ header
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        ExchangeRequest request = exchangeRequestService.createExchangeRequest(token, dto);
        return ResponseEntity.ok(request);
    }

    // 2. Lấy danh sách yêu cầu của user hiện tại
    @GetMapping("/my-requests")
    public ResponseEntity<List<ExchangeRequest>> getMyRequests(
            @RequestHeader("Authorization") String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<ExchangeRequest> requests = exchangeRequestService.getMyRequests(token);
        return ResponseEntity.ok(requests);
    }

    // 3. Lấy tất cả yêu cầu (dành cho admin)
    @GetMapping("/all")
    public ResponseEntity<List<ExchangeRequest>> getAllRequests() {
        List<ExchangeRequest> requests = exchangeRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    // 4. Cập nhật trạng thái yêu cầu (dành cho admin)
    @PutMapping("/{id}/status")
    public ResponseEntity<ExchangeRequest> updateStatus(
            @PathVariable Long id,
            @RequestParam ExchangeStatus status) {

        ExchangeRequest updatedRequest = exchangeRequestService.updateStatus(id, status);
        return ResponseEntity.ok(updatedRequest);
    }
}
