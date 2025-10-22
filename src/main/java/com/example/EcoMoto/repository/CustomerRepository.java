package com.example.EcoMoto.repository;


import com.example.EcoMoto.entity.Customer;
import com.example.EcoMoto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    Optional<Customer> findByUser(User user);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    <T> Optional<T> findByUserId(Long userId);
}
