package com.example.EcoMoto.service.Impl;

import com.example.EcoMoto.entity.CompareHistory;
import com.example.EcoMoto.entity.Product;
import com.example.EcoMoto.entity.User;
import com.example.EcoMoto.repository.CompareHistoryRepository;
import com.example.EcoMoto.repository.ProductRepository;
import com.example.EcoMoto.repository.UserRepository;
import com.example.EcoMoto.service.service.CompareService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompareServiceImpl implements CompareService {

    private final ProductRepository productRepository;
    private final CompareHistoryRepository compareHistoryRepository;
    private final UserRepository userRepository;
    public CompareServiceImpl(ProductRepository productRepository,
                              CompareHistoryRepository compareHistoryRepository,
                              UserRepository userRepository) {
        this.productRepository = productRepository;
        this.compareHistoryRepository = compareHistoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Map<String, Object> compareProducts(List<Long> productIds, UserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();

        // Lấy danh sách sản phẩm
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() < 2) {
            throw new IllegalArgumentException("Cần ít nhất 2 sản phẩm để so sánh");
        }

        // Nếu có user đăng nhập thì lưu lịch sử
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User không tồn tại"));

            CompareHistory history = new CompareHistory();
            history.setUser(user);
            history.setProducts(products);
            history.setComparedAt(LocalDateTime.now());

            compareHistoryRepository.save(history);
        }

        // Chuẩn bị dữ liệu trả về
        List<Map<String, Object>> productData = products.stream().map(p -> {
            Map<String, Object> data = new HashMap<>();
            data.put("id", p.getId());
            data.put("name", p.getName());
            data.put("brand", p.getBrand().getName());
            data.put("color", p.getColor());
            data.put("price", p.getPrice());
            return data;
        }).collect(Collectors.toList());

        response.put("products", productData);
        response.put("count", products.size());

        return response;
    }
    @Override
    public List<Map<String, Object>> getCompareHistory(UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("Bạn cần đăng nhập để xem lịch sử so sánh");
        }

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        List<CompareHistory> histories = compareHistoryRepository.findByUser(user);

        return histories.stream().map(history -> {
            Map<String, Object> data = new HashMap<>();
            data.put("id", history.getId());
            data.put("comparedAt", history.getComparedAt());

            List<Map<String, Object>> products = history.getProducts().stream().map(p -> {
                Map<String, Object> pData = new HashMap<>();
                pData.put("id", p.getId());
                pData.put("name", p.getName());
                pData.put("brand", p.getBrand().getName());
                pData.put("color", p.getColor());
                pData.put("price", p.getPrice());
                return pData;
            }).collect(Collectors.toList());

            data.put("products", products);
            return data;
        }).collect(Collectors.toList());
    }

}

