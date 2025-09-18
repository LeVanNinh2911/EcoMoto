package com.example.EcoMoto.repository;


import com.example.EcoMoto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm user theo username
    Optional<User> findByUsername(String username);

    // Tìm user theo email
    Optional<User> findByEmail(String email);

    // Hữu ích cho login (username hoặc email)
    Optional<User> findByUsernameOrEmail(String username, String email);

    // Kiểm tra tồn tại
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Lấy danh sách user theo role name (vd: "ADMIN", "USER")
    List<User> findAllByRole_Name(String roleName);

    // Lấy danh sách user theo trạng thái (ACTIVE / INACTIVE)
    List<User> findAllByStatus(User.Status status);

    // Tìm user theo id và trạng thái (thường dùng để đảm bảo account đang active)
    Optional<User> findByIdAndStatus(Long id, User.Status status);
}

