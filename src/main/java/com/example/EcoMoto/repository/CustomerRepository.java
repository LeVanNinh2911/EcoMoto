package com.example.EcoMoto.repository;


import com.example.EcoMoto.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Tìm khách hàng theo email
    Optional<Customer> findByEmail(String email);

    // Tìm khách hàng theo số điện thoại
    Optional<Customer> findByPhone(String phone);

    // Kiểm tra khách hàng có tồn tại theo email
    boolean existsByEmail(String email);

    // Kiểm tra khách hàng có tồn tại theo số điện thoại
    boolean existsByPhone(String phone);
}
