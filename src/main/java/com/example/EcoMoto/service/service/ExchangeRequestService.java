package com.example.EcoMoto.service.service;

import com.example.EcoMoto.dto.exchange.ExchangeResponseDto;
import com.example.EcoMoto.entity.enums.ExchangeStatus;
import com.example.EcoMoto.dto.exchange.ExchangeRequestDto;

import java.util.List;

public interface ExchangeRequestService {
    ExchangeResponseDto createExchangeRequest(String token, ExchangeRequestDto dto);
    List<ExchangeResponseDto> getMyRequests(String token);
    List<ExchangeResponseDto> getAllRequests();
    ExchangeResponseDto updateStatus(Long id, ExchangeStatus status);
}

