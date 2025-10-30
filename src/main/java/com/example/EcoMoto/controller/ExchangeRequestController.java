package com.example.EcoMoto.controller;

import com.example.EcoMoto.dto.exchange.ExchangeRequestDto;
import com.example.EcoMoto.dto.exchange.ExchangeResponseDto;
import com.example.EcoMoto.entity.enums.ExchangeStatus;
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

    // ================== Tạo yêu cầu đổi xe ==================
    @PostMapping("/create")
    public ResponseEntity<ExchangeResponseDto> createExchangeRequest(
            @RequestHeader("Authorization") String token,
            @RequestBody ExchangeRequestDto dto
    ) {
        // Loại bỏ prefix "Bearer " nếu có
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        ExchangeResponseDto response = exchangeRequestService.createExchangeRequest(token, dto);
        return ResponseEntity.ok(response);
    }

    // ================== Lấy danh sách yêu cầu của user ==================
    @GetMapping("/my-requests")
    public ResponseEntity<List<ExchangeResponseDto>> getMyRequests(
            @RequestHeader("Authorization") String token
    ) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<ExchangeResponseDto> requests = exchangeRequestService.getMyRequests(token);
        return ResponseEntity.ok(requests);
    }

    // ================== Lấy tất cả yêu cầu (cho admin) ==================
    @GetMapping("/all")
    public ResponseEntity<List<ExchangeResponseDto>> getAllRequests() {
        List<ExchangeResponseDto> requests = exchangeRequestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    // ================== Cập nhật trạng thái yêu cầu ==================
    @PutMapping("/{id}/status")
    public ResponseEntity<ExchangeResponseDto> updateStatus(
            @PathVariable Long id,
            @RequestParam ExchangeStatus status
    ) {
        ExchangeResponseDto updated = exchangeRequestService.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}

