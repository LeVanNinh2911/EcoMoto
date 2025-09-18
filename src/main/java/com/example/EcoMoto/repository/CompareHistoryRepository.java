package com.example.EcoMoto.repository;

import com.example.EcoMoto.entity.CompareHistory;
import com.example.EcoMoto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompareHistoryRepository extends JpaRepository<CompareHistory, Long> {
    List<CompareHistory> findByUser(User user);
}


