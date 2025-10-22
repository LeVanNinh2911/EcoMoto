package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.entity.Product;
import com.example.EcoMoto.entity.Promotion;
import com.example.EcoMoto.repository.ProductRepository;
import com.example.EcoMoto.repository.PromotionRepository;
import com.example.EcoMoto.service.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private  ProductRepository productRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository, ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> getPromotionById(Long id) {
        return promotionRepository.findById(id);
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion updatePromotion(Long id, Promotion promotionDetails) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        promotion.setName(promotionDetails.getName());
        promotion.setDescription(promotionDetails.getDescription());
        promotion.setDiscountType(promotionDetails.getDiscountType());
        promotion.setDiscountValue(promotionDetails.getDiscountValue());
        promotion.setStartDate(promotionDetails.getStartDate());
        promotion.setEndDate(promotionDetails.getEndDate());
        promotion.setActive(promotionDetails.isActive());
        return promotionRepository.save(promotion);
    }

    @Override
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }

    // ✅ Hàm gán khuyến mãi cho sản phẩm
    @Override
    public Promotion assignProductToPromotion(Long promotionId, Long productId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Gán 2 chiều
        promotion.getProducts().add(product);
        product.getPromotions().add(promotion);

        productRepository.save(product); // lưu để cập nhật bảng liên kết
        return promotionRepository.save(promotion);
    }
    public Product removePromotionFromProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setPromotions(null);
        return productRepository.save(product);
    }

}
