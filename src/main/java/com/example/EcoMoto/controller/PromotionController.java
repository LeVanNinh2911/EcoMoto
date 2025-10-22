package com.example.EcoMoto.controller;

import com.example.EcoMoto.entity.Product;
import com.example.EcoMoto.entity.Promotion;
import com.example.EcoMoto.service.Impl.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    @Autowired
    private PromotionServiceImpl promotionService;

    public PromotionController(PromotionServiceImpl promotionService) {
        this.promotionService = promotionService;
    }

    @GetMapping
    public List<Promotion> getAllPromotions() {
        return promotionService.getAllPromotions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
        return promotionService.getPromotionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Promotion createPromotion(@RequestBody Promotion promotion) {
        return promotionService.createPromotion(promotion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable Long id, @RequestBody Promotion promotionDetails) {
        return ResponseEntity.ok(promotionService.updatePromotion(id, promotionDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{promotionId}/assign-product/{productId}")
    public ResponseEntity<Promotion> assignProductToPromotion(
            @PathVariable Long promotionId,
            @PathVariable Long productId) {

        Promotion updatedPromotion = promotionService.assignProductToPromotion(promotionId, productId);
        return ResponseEntity.ok(updatedPromotion);
    }
    @DeleteMapping("/remove-promotion/{productId}")
    public ResponseEntity<Product> removePromotionFromProduct(@PathVariable Long productId) {
        Product updated = promotionService.removePromotionFromProduct(productId);
        return ResponseEntity.ok(updated);
    }


}

