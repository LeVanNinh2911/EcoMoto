package com.example.EcoMoto.service.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface CompareService {
    Map<String, Object> compareProducts(List<Long> productIds, UserDetails userDetails);
    List<Map<String, Object>> getCompareHistory(UserDetails userDetails);
}

