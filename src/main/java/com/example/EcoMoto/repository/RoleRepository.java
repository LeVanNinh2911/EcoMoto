package com.example.EcoMoto.repository;

import com.example.EcoMoto.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Tìm role theo tên (ví dụ: "ADMIN", "USER")
    Optional<Role> findByName(String name);

    // Kiểm tra role có tồn tại không
    boolean existsByName(String name);

    // Lấy tất cả role có tên chứa một đoạn text (dùng cho tìm kiếm)
    List<Role> findByNameContainingIgnoreCase(String keyword);
}

