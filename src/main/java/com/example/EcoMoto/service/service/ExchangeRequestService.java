package com.example.EcoMoto.service.service;

import com.example.EcoMoto.entity.enums.ExchangeStatus;
import com.example.EcoMoto.dto.exchange.ExchangeRequestDto;
import com.example.EcoMoto.entity.ExchangeRequest;

import java.util.List;

public interface ExchangeRequestService {
    ExchangeRequest createExchangeRequest(String token, ExchangeRequestDto dto);
    List<ExchangeRequest> getMyRequests(String token);
    List<ExchangeRequest> getAllRequests();
    ExchangeRequest updateStatus(Long id, ExchangeStatus status);
}

