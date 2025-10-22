package com.example.EcoMoto.service.service;
import com.example.EcoMoto.entity.Promotion;

import java.util.List;
import java.util.Optional;

public interface PromotionService {
    List<Promotion> getAllPromotions();
    Optional<Promotion> getPromotionById(Long id);
    Promotion createPromotion(Promotion promotion);
    Promotion updatePromotion(Long id, Promotion promotionDetails);
    void deletePromotion(Long id);

    // ✅ Thêm mới
    Promotion assignProductToPromotion(Long promotionId, Long productId);
}

