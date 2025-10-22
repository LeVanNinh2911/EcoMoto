package com.example.EcoMoto.service.service;

import com.example.EcoMoto.dto.compare.CompareRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface CompareService {
    Map<String, Object> compareProducts(CompareRequest request, UserDetails userDetails);
    List<Map<String, Object>> getCompareHistory(UserDetails userDetails);
}

