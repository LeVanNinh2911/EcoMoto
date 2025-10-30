package com.example.EcoMoto.repository;

import com.example.EcoMoto.entity.ExchangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExchangeRequestRepository extends JpaRepository<ExchangeRequest, Long> {
    List<ExchangeRequest> findByUserId(Long UserId);
}

